$bytes = [System.IO.File]::ReadAllBytes('d:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java')
$first3 = $bytes[0..2]
'First 3 bytes: ' + ($first3 | ForEach-Object { '0x{0:X2}' -f $_ })
