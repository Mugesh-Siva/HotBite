$baseDir = "d:\Hexaware_Phase_2_react_springboot\CaseStudyProject\hotbyte\src\main\java\com\hexaware\hotbyte"
$oldServiceDir = "$baseDir\service"
$newSecurityDir = "$baseDir\security"
$newServiceImplDir = "$baseDir\service\impl"

# Create new directories if they don't exist
if (!(Test-Path $newSecurityDir)) { New-Item -ItemType Directory -Force -Path $newSecurityDir }
if (!(Test-Path $newServiceImplDir)) { New-Item -ItemType Directory -Force -Path $newServiceImplDir }

# Move and rename files
$oldUserDetails = "$oldServiceDir\UserDetailsImp.java"
$newUserDetails = "$newSecurityDir\UserDetailsImpl.java"
if (Test-Path $oldUserDetails) { Move-Item -Path $oldUserDetails -Destination $newUserDetails }

$oldUserDetailsService = "$oldServiceDir\UserDetailsServiceImp.java"
$newUserDetailsService = "$newServiceImplDir\UserDetailsServiceImpl.java"
if (Test-Path $oldUserDetailsService) { Move-Item -Path $oldUserDetailsService -Destination $newUserDetailsService }

# Replace references in all .java files
$files = Get-ChildItem -Path $baseDir -Recurse -Filter *.java

foreach ($file in $files) {
    $content = Get-Content $file.FullName -Raw
    $originalContent = $content

    # Package updates for the moved files
    if ($file.FullName -eq $newUserDetails) {
        $content = $content -replace "package com.hexaware.hotbyte.service;", "package com.hexaware.hotbyte.security;"
    }
    if ($file.FullName -eq $newUserDetailsService) {
        $content = $content -replace "package com.hexaware.hotbyte.service;", "package com.hexaware.hotbyte.service.impl;"
    }

    # Global replacements
    $content = $content -replace "com.hexaware.hotbyte.service.UserDetailsImp", "com.hexaware.hotbyte.security.UserDetailsImpl"
    $content = $content -replace "\bUserDetailsImp\b", "UserDetailsImpl"
    
    $content = $content -replace "com.hexaware.hotbyte.service.UserDetailsServiceImp", "com.hexaware.hotbyte.service.impl.UserDetailsServiceImpl"
    $content = $content -replace "\bUserDetailsServiceImp\b", "UserDetailsServiceImpl"

    if ($content -cne $originalContent) {
        Set-Content -Path $file.FullName -Value $content -NoNewline
        Write-Host "Updated: $($file.FullName)"
    }
}
Write-Host "Done"
