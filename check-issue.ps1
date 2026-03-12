$content = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java' -Raw

# Check for lines that might have issues with comments and code
$lines = $content -split "`r?`n"

# Check around line 993-994 (0-indexed: 992-993)
for ($i = 990; $i -lt 1000; $i++) {
    $line = $lines[$i]
    $lineNum = $i + 1
    # Check if line contains // and code after it
    if ($line -match '//.*\S.*[^/]\s*\S') {
        "Line $lineNum has comment + code: $line"
    }
}
