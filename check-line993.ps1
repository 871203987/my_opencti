$content = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java' -Raw
$lines = $content -split "`r?`n"
$line = $lines[992]  # 0-indexed, so 992 is line 993
"Line 993 content:"
$line
""
"Line 993 hex dump:"
$bytes = [System.Text.Encoding]::UTF8.GetBytes($line)
for ($i = 0; $i -lt $bytes.Length; $i++) {
    $char = [char]$bytes[$i]
    if ($bytes[$i] -ge 0x20 -and $bytes[$i] -le 0x7E) {
        "[{0,4}] 0x{1:X2} '{2}'" -f $i, $bytes[$i], $char
    } else {
        "[{0,4}] 0x{1:X2} ." -f $i, $bytes[$i]
    }
}
