$content = Get-Content 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java' -Raw
$open = ($content -split '\{' ).Count - 1
$close = ($content -split '\}' ).Count - 1
"Open braces: $open"
"Close braces: $close"
"Difference: $($open - $close)"
