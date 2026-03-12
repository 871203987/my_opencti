$content = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java' -Raw
# Check for any special characters around line 1005-1008
$lines = $content -split "`r?`n"
for ($i = 1000; $i -lt 1015; $i++) {
    $line = $lines[$i]
    $lineNum = $i + 1
    $chars = [System.Text.Encoding]::UTF8.GetBytes($line)
    $hexChars = ($chars | ForEach-Object { '0x{0:X2}' -f $_ }) -join ' '
    "Line $lineNum : $line"
    "Line $lineNum hex: $hexChars"
    "---"
}
