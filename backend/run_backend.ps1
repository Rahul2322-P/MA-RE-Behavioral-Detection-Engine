# Self-Contained Backend Launcher Script for Spring Boot
# Automatically checks for Maven or downloads a portable Apache Maven instance if needed

Write-Host "======================================================================" -ForegroundColor Cyan
Write-Host "  Starting Spring Boot Backend Server (LOLBin Behavioral Detector)  " -ForegroundColor Cyan
Write-Host "======================================================================" -ForegroundColor Cyan

$backendDir = $PSScriptRoot
Set-Location $backendDir

# Find Maven Command (Global or Portable)
$mvnCmd = $null
if (Get-Command mvn -ErrorAction SilentlyContinue) {
    Write-Host "[OK] Using System Maven (mvn)" -ForegroundColor Green
    $mvnCmd = "mvn"
} elseif (Test-Path "$backendDir\.maven\apache-maven-3.9.6\bin\mvn.cmd") {
    Write-Host "[OK] Using Portable Apache Maven (.maven\apache-maven-3.9.6)" -ForegroundColor Green
    $mvnCmd = "$backendDir\.maven\apache-maven-3.9.6\bin\mvn.cmd"
} else {
    Write-Host "[!] Apache Maven not found on system. Downloading portable Maven 3.9.6..." -ForegroundColor Yellow
    $mavenZip = "$backendDir\.maven\maven.zip"
    $mavenExtractDir = "$backendDir\.maven"
    if (-not (Test-Path $mavenExtractDir)) {
        New-Item -ItemType Directory -Path $mavenExtractDir | Out-Null
    }
    $zipUrl = "https://archive.apache.org/dist/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.zip"
    try {
        Invoke-WebRequest -Uri $zipUrl -OutFile $mavenZip
        Write-Host "[...] Extracting portable Maven..." -ForegroundColor Cyan
        Expand-Archive -Path $mavenZip -DestinationPath $mavenExtractDir -Force
        Remove-Item $mavenZip -Force
        $mvnCmd = "$backendDir\.maven\apache-maven-3.9.6\bin\mvn.cmd"
        Write-Host "[OK] Portable Maven ready!" -ForegroundColor Green
    } catch {
        Write-Host "[ERROR] Could not download portable Maven. Please ensure internet access or install Apache Maven." -ForegroundColor Red
        Pause
        exit
    }
}

# Check MySQL connection on port 3306
$mysqlRunning = $false
try {
    $tcp = New-Object System.Net.Sockets.TcpClient
    $tcp.Connect("127.0.0.1", 3306)
    $mysqlRunning = $true
    $tcp.Close()
} catch {
    $mysqlRunning = $false
}

if ($mysqlRunning) {
    Write-Host "[✓] MySQL detected on port 3306. Starting with MySQL database..." -ForegroundColor Green
    & $mvnCmd spring-boot:run
} else {
    Write-Host "[!] MySQL is NOT running on port 3306." -ForegroundColor Yellow
    Write-Host "[*] Starting automatically with embedded H2 database mode (zero setup needed)..." -ForegroundColor Cyan
    & $mvnCmd spring-boot:run "-Dspring-boot.run.profiles=h2"
}
