$file = 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\store\StoreEntity.java'
Write-Host "Checking $file"
$lines = Get-Content $file
for ($i = 0; $i -lt $lines.Count; $i++) {
    $line = $lines[$i]
    # Check if line has // followed by code on same line (field declaration)
    if ($line -match '//.*[^/]\S+.*\s+(protected|private|public)\s+\w+.*\s+\w+.*;') {
        $lineNum = $i + 1
        Write-Host "  Line $lineNum : $line"
    }
}
