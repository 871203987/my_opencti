$files = @(
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixUtils.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\converter\StixConverterUtils.java'
)

foreach ($file in $files) {
    $bytes = [System.IO.File]::ReadAllBytes($file)
    $hasBom = ($bytes[0] -eq 0xEF -and $bytes[1] -eq 0xBB -and $bytes[2] -eq 0xBF)
    if ($hasBom) {
        Write-Host "$file has BOM"
        # Remove BOM
        $newBytes = $bytes[3..($bytes.Length-1)]
        [System.IO.File]::WriteAllBytes($file, $newBytes)
        Write-Host "  -> Fixed"
    } else {
        Write-Host "$file no BOM"
    }
}
