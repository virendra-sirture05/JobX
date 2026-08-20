$ErrorActionPreference = "Continue"

$ROOT = Get-Location

# ============================================================
# Configuration
# ============================================================

$CLOUD_SERVICES = @(
    @{
        Name = "job-portal-service-registry"
        Path = Join-Path $ROOT "cloud\job-portal-service-registry"
        HealthUrl = "http://localhost:8761/actuator/health"
    },
    @{
        Name = "job-portal-config-server"
        Path = Join-Path $ROOT "cloud\job-portal-config-server"
        HealthUrl = "http://localhost:8888/actuator/health"
    },
    @{
        Name = "job-portal-api-gateway"
        Path = Join-Path $ROOT "cloud\job-portal-api-gateway"
        HealthUrl = "http://localhost:5000/actuator/health"
    }
)

$APPLICATION_SERVICES = @(
    @{
        Name = "job-portal-user-service"
        Path = Join-Path $ROOT "services\job-portal-user-service"
    },
    @{
        Name = "job-portal-company-service"
        Path = Join-Path $ROOT "services\job-portal-company-service"
    },
    @{
        Name = "job-portal-job-service"
        Path = Join-Path $ROOT "services\job-portal-job-service"
    },
    @{
        Name = "job-portal-application-service"
        Path = Join-Path $ROOT "services\job-portal-application-service"
    },
    @{
        Name = "job-portal-notification-service"
        Path = Join-Path $ROOT "services\job-portal-notification-service"
    },
    @{
        Name = "job-portal-preferences"
        Path = Join-Path $ROOT "services\job-portal-preferences"
    },
    @{
        Name = "job-portal-resume-service"
        Path = Join-Path $ROOT "services\job-portal-resume-service"
    },
    @{
        Name = "job-portal-ai-service"
        Path = Join-Path $ROOT "services\job-portal-ai-service"
    }
)

# ============================================================
# Function: Start Spring Boot Service
# ============================================================

function Start-SpringBootService {
    param (
        [string]$Name,
        [string]$Path
    )

    Write-Host ""
    Write-Host "============================================================" -ForegroundColor Cyan
    Write-Host "Starting: $Name" -ForegroundColor Cyan
    Write-Host "Path:    $Path" -ForegroundColor DarkCyan
    Write-Host "============================================================" -ForegroundColor Cyan

    if (!(Test-Path $Path)) {
        Write-Host "ERROR: Directory does not exist: $Path" -ForegroundColor Red
        return $null
    }

    $process = Start-Process `
        -FilePath "powershell.exe" `
        -ArgumentList @(
            "-NoExit",
            "-NoProfile",
            "-Command",
            "Set-Location '$Path'; Write-Host 'Starting $Name...' -ForegroundColor Cyan; mvn spring-boot:run"
        ) `
        -PassThru

    if ($null -eq $process) {
        Write-Host "ERROR: Could not start $Name" -ForegroundColor Red
        return $null
    }

    Write-Host "Started $Name" -ForegroundColor Green
    Write-Host "PowerShell PID: $($process.Id)" -ForegroundColor Gray

    return $process
}

# ============================================================
# Function: Wait for Health
# ============================================================

function Wait-ForHealth {
    param (
        [string]$Name,
        [string]$HealthUrl,
        [System.Diagnostics.Process]$Process,
        [int]$TimeoutSeconds = 180
    )

    Write-Host ""
    Write-Host "Waiting for $Name to become healthy..." -ForegroundColor Yellow
    Write-Host "Health URL: $HealthUrl" -ForegroundColor Gray

    $startTime = Get-Date

    while ($true) {

        # Check whether launcher process has exited
        if ($Process.HasExited) {
            Write-Host ""
            Write-Host "ERROR: $Name process exited." -ForegroundColor Red
            return $false
        }

        try {
            $response = Invoke-WebRequest `
                -Uri $HealthUrl `
                -UseBasicParsing `
                -TimeoutSec 5 `
                -ErrorAction Stop

            if ($response.StatusCode -eq 200) {
                Write-Host ""
                Write-Host "$Name is HEALTHY" -ForegroundColor Green
                return $true
            }
        }
        catch {
            # Application is still starting
        }

        $elapsed = ((Get-Date) - $startTime).TotalSeconds

        if ($elapsed -ge $TimeoutSeconds) {
            Write-Host ""
            Write-Host "ERROR: $Name did not become healthy within $TimeoutSeconds seconds." -ForegroundColor Red
            return $false
        }

        Write-Host "." -NoNewline
        Start-Sleep -Seconds 5
    }
}

# ============================================================
# PHASE 1
# Registry
# ============================================================

Write-Host ""
Write-Host "############################################################" -ForegroundColor Magenta
Write-Host "# PHASE 1: SERVICE REGISTRY" -ForegroundColor Magenta
Write-Host "############################################################" -ForegroundColor Magenta

$registry = $CLOUD_SERVICES[0]

$registryProcess = Start-SpringBootService `
    -Name $registry.Name `
    -Path $registry.Path

if ($null -eq $registryProcess) {
    Write-Host "Registry failed to start. Aborting." -ForegroundColor Red
    exit 1
}

if (!(Wait-ForHealth `
        -Name $registry.Name `
        -HealthUrl $registry.HealthUrl `
        -Process $registryProcess)) {

    Write-Host ""
    Write-Host "REGISTRY FAILED. STARTUP ABORTED." -ForegroundColor Red
    exit 1
}

# ============================================================
# PHASE 2
# Config Server
# ============================================================

Write-Host ""
Write-Host "############################################################" -ForegroundColor Magenta
Write-Host "# PHASE 2: CONFIG SERVER" -ForegroundColor Magenta
Write-Host "############################################################" -ForegroundColor Magenta

$config = $CLOUD_SERVICES[1]

$configProcess = Start-SpringBootService `
    -Name $config.Name `
    -Path $config.Path

if ($null -eq $configProcess) {
    Write-Host "Config Server failed to start. Aborting." -ForegroundColor Red
    exit 1
}

if (!(Wait-ForHealth `
        -Name $config.Name `
        -HealthUrl $config.HealthUrl `
        -Process $configProcess)) {

    Write-Host ""
    Write-Host "CONFIG SERVER FAILED. STARTUP ABORTED." -ForegroundColor Red
    exit 1
}

# ============================================================
# PHASE 3
# API Gateway
# ============================================================

Write-Host ""
Write-Host "############################################################" -ForegroundColor Magenta
Write-Host "# PHASE 3: API GATEWAY" -ForegroundColor Magenta
Write-Host "############################################################" -ForegroundColor Magenta

$gateway = $CLOUD_SERVICES[2]

$gatewayProcess = Start-SpringBootService `
    -Name $gateway.Name `
    -Path $gateway.Path

if ($null -eq $gatewayProcess) {
    Write-Host "API Gateway failed to start. Aborting." -ForegroundColor Red
    exit 1
}

if (!(Wait-ForHealth `
        -Name $gateway.Name `
        -HealthUrl $gateway.HealthUrl `
        -Process $gatewayProcess)) {

    Write-Host ""
    Write-Host "API GATEWAY FAILED. STARTUP ABORTED." -ForegroundColor Red
    exit 1
}

# ============================================================
# PHASE 4
# APPLICATION MICROSERVICES
# ============================================================

Write-Host ""
Write-Host "############################################################" -ForegroundColor Magenta
Write-Host "# PHASE 4: APPLICATION MICROSERVICES" -ForegroundColor Magenta
Write-Host "############################################################" -ForegroundColor Magenta

$failedServices = @()

foreach ($service in $APPLICATION_SERVICES) {

    Write-Host ""
    Write-Host "------------------------------------------------------------" -ForegroundColor DarkCyan
    Write-Host "Starting application service: $($service.Name)" -ForegroundColor Cyan
    Write-Host "------------------------------------------------------------" -ForegroundColor DarkCyan

    $process = Start-SpringBootService `
        -Name $service.Name `
        -Path $service.Path

    if ($null -eq $process) {
        Write-Host "$($service.Name) FAILED TO START" -ForegroundColor Red
        $failedServices += $service.Name
        continue
    }

    # IMPORTANT:
    # We intentionally DO NOT wait for application-service health here.
    #
    # If one application service fails, the next service will still start.
    #
    Write-Host "$($service.Name) launched. Continuing to next service..." -ForegroundColor Green
}

# ============================================================
# FINAL SUMMARY
# ============================================================

Write-Host ""
Write-Host ""
Write-Host "############################################################" -ForegroundColor Magenta
Write-Host "# STARTUP COMPLETE" -ForegroundColor Magenta
Write-Host "############################################################" -ForegroundColor Magenta

if ($failedServices.Count -eq 0) {

    Write-Host ""
    Write-Host "Cloud infrastructure:" -ForegroundColor Green
    Write-Host "  Registry       : RUNNING" -ForegroundColor Green
    Write-Host "  Config Server  : RUNNING" -ForegroundColor Green
    Write-Host "  API Gateway    : RUNNING" -ForegroundColor Green

    Write-Host ""
    Write-Host "All application services were launched." -ForegroundColor Green

}
else {

    Write-Host ""
    Write-Host "Cloud infrastructure:" -ForegroundColor Green
    Write-Host "  Registry       : RUNNING" -ForegroundColor Green
    Write-Host "  Config Server  : RUNNING" -ForegroundColor Green
    Write-Host "  API Gateway    : RUNNING" -ForegroundColor Green

    Write-Host ""
    Write-Host "The following application services failed to launch:" -ForegroundColor Red

    foreach ($failed in $failedServices) {
        Write-Host "  - $failed" -ForegroundColor Red
    }

    Write-Host ""
    Write-Host "Other application services were still launched." -ForegroundColor Yellow
}

Write-Host ""
Write-Host "Local startup process finished." -ForegroundColor Cyan