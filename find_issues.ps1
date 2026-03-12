$files = @(
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixUtils.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\converter\StixConverterUtils.java'
)

foreach ($file in $files) {
    Write-Host "Checking $file"
    $lines = Get-Content $file
    for ($i = 0; $i -lt $lines.Count; $i++) {
        $line = $lines[$i]
        # Check if line has comment followed by code on same line
        if ($line -match '//.*\S+.*\s+(public|private|protected|if|for|while|return|class|interface|void|static|final|\w+\s+\w+\s*\()') {
            $lineNum = $i + 1
            Write-Host "  Line $lineNum : $line"
        }
    }
}
