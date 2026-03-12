$filePath = 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java'
$content = Get-Content $filePath -Raw

# Fix line 993: split comment and code
$content = $content -replace '(// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable.*?)(// 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞？)(\s*if \(isStixCyberObservable\(toType\)\) \{)', "`$1`r`n        `$2`r`n        `$3"

# Fix line 1003: split comment and code
$content = $content -replace '(// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable.*?)(\s*if \(isStixCyberObservable\(fromType\)\) \{)', "`$1`r`n        `$2"

# Fix line 1071: split comment and code  
$content = $content -replace '(// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable.*?)(\s*if \(isStixCyberObservable\(toType\)\) \{)', "`$1`r`n            `$2"

# Fix line 1079: split comment and code
$content = $content -replace '(// 濡傛灉婧愮被鍨嬫槸Cyber Observable.*?)(\s*if \(isStixCyberObservable\(fromType\)\) \{)', "`$1`r`n            `$2"

Set-Content $filePath $content -NoNewline
"Fixed comments in StixCoreRelationshipsMapping.java"
