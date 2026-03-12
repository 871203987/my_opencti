$lines = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java'
'Line 1006: ' + $lines[1005]
'Line 1007: ' + $lines[1006]
'Line 1008: ' + $lines[1007]
'Line 1007 length: ' + $lines[1006].Length
'Line 1007 bytes: '
[System.Text.Encoding]::UTF8.GetBytes($lines[1006]) | ForEach-Object { '0x{0:X2}' -f $_ } | Join-String -Separator ' '
