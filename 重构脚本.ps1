# STIX模块重构脚本
# 一比一重写方案实施脚本

Write-Host "开始STIX模块重构..." -ForegroundColor Green

# 1. 删除旧的common.types目录（除了stix和store）
Write-Host "步骤1: 清理旧目录..." -ForegroundColor Yellow

# 2. 移动common/types/stix/* 到 types/stix/common/
Write-Host "步骤2: 移动stix common类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\stix"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\common"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.stix;', 'package io.opencti.types.stix.common;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 3. 移动common/types/stix/sdo/* 到 types/stix/sdo/
Write-Host "步骤3: 移动SDO类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\stix\sdo"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sdo"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.stix.sdo;', 'package io.opencti.types.stix.sdo;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 4. 移动common/types/stix/sco/* 到 types/stix/sco/
Write-Host "步骤4: 移动SCO类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\stix\sco"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.stix.sco;', 'package io.opencti.types.stix.sco;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 5. 移动common/types/stix/sro/* 到 types/stix/sro/
Write-Host "步骤5: 移动SRO类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\stix\sro"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sro"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.stix.sro;', 'package io.opencti.types.stix.sro;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 6. 移动common/types/stix/smo/* 到 types/stix/smo/
Write-Host "步骤6: 移动SMO类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\stix\smo"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\smo"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.stix.smo;', 'package io.opencti.types.stix.smo;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 7. 移动common/types/store/* 到 types/store/
Write-Host "步骤7: 移动Store类..." -ForegroundColor Yellow
$sourceDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types\store"
$targetDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\store"

if (Test-Path $sourceDir) {
    Get-ChildItem -Path $sourceDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新包名
        $content = $content -replace 'package io.opencti.common.types.store;', 'package io.opencti.types.store;'
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        
        # 保存到新位置
        $newPath = Join-Path $targetDir $_.Name
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: $($_.Name)"
    }
}

# 8. 更新database/stix/converter/* 中的import
Write-Host "步骤8: 更新converter类..." -ForegroundColor Yellow
$converterDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\converter"
if (Test-Path $converterDir) {
    Get-ChildItem -Path $converterDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新import
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        $content = $content -replace 'import io.opencti.database.stix.model.', 'import io.opencti.types.stix.common.'
        
        Set-Content -Path $_.FullName -Value $content -Encoding UTF8
        Write-Host "  Updated: $($_.Name)"
    }
}

# 9. 更新database/stix/* 中的import
Write-Host "步骤9: 更新database/stix类..." -ForegroundColor Yellow
$databaseStixDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix"
if (Test-Path $databaseStixDir) {
    Get-ChildItem -Path $databaseStixDir -File | ForEach-Object {
        $content = Get-Content $_.FullName -Raw
        # 更新import
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        $content = $content -replace 'import io.opencti.database.stix.model.', 'import io.opencti.types.stix.common.'
        
        Set-Content -Path $_.FullName -Value $content -Encoding UTF8
        Write-Host "  Updated: $($_.Name)"
    }
}

# 10. 删除旧的common.types目录
Write-Host "步骤10: 删除旧目录..." -ForegroundColor Yellow
$oldDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\common\types"
if (Test-Path $oldDir) {
    Remove-Item -Path $oldDir -Recurse -Force
    Write-Host "  Deleted: $oldDir"
}

# 11. 删除空的mapping目录
$mappingDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\mapping"
if (Test-Path $mappingDir) {
    Remove-Item -Path $mappingDir -Recurse -Force
    Write-Host "  Deleted: $mappingDir"
}

# 12. 删除空的model目录
$modelDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\model"
if (Test-Path $modelDir) {
    Remove-Item -Path $modelDir -Recurse -Force
    Write-Host "  Deleted: $modelDir"
}

# 13. 删除空的representative目录
$repDir = "d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\representative"
if (Test-Path $repDir) {
    # 移动StixRepresentative.java到上级目录
    $repFile = Join-Path $repDir "StixRepresentative.java"
    if (Test-Path $repFile) {
        $content = Get-Content $repFile -Raw
        $content = $content -replace 'import io.opencti.common.types.stix.', 'import io.opencti.types.stix.common.'
        $content = $content -replace 'import io.opencti.common.types.store.', 'import io.opencti.types.store.'
        $newPath = Join-Path $databaseStixDir "StixRepresentative.java"
        Set-Content -Path $newPath -Value $content -Encoding UTF8
        Write-Host "  Moved: StixRepresentative.java"
    }
    Remove-Item -Path $repDir -Recurse -Force
    Write-Host "  Deleted: $repDir"
}

Write-Host "`nRefactoring completed!" -ForegroundColor Green
Write-Host "Please run 'mvn clean compile -DskipTests' to verify compilation" -ForegroundColor Cyan
