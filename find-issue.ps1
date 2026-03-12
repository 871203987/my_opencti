$content = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java' -Raw
$lines = $content -split "`r?`n"

# Find lines that contain "if (isStixCyberObservable"
for ($i = 0; $i -lt $lines.Count; $i++) {
    $line = $lines[$i]
    $lineNum = $i + 1
    if ($line -match 'if \(isStixCyberObservable') {
        "Line $lineNum : $line"
    }
}
