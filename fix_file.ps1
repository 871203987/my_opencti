$filePath = 'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java'

# Read file as bytes
$bytes = [System.IO.File]::ReadAllBytes($filePath)
$content = [System.Text.Encoding]::UTF8.GetString($bytes)

# Fix the issues - replace the problematic lines
# Pattern: comment // comment        if (
# Should be: comment
#            // comment
#            if (

# Fix 1
$content = $content -replace '(?m)(^\s*// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable[^
]*?)
(\s*// 杩欎簺鍏崇郴鏄疭TIX[^
]*?)
(\s*if \(isStixCyberObservable\(toType\)\))', "`$1`r`n`$2`r`n`$3"

# Fix 2
$content = $content -replace '(?m)(^\s*// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable[^
]*?)
(\s*if \(isStixCyberObservable\(fromType\)\))', "`$1`r`n`$2"

# Fix 3
$content = $content -replace '(?m)(^\s*// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable[^
]*?)
(\s*if \(isStixCyberObservable\(toType\)\))', "`$1`r`n`$2"

# Fix 4
$content = $content -replace '(?m)(^\s*// 濡傛灉婧愮被鍨嬫槸Cyber Observable[^
]*?)
(\s*if \(isStixCyberObservable\(fromType\)\))', "`$1`r`n`$2"

# Write back
[System.IO.File]::WriteAllBytes($filePath, [System.Text.Encoding]::UTF8.GetBytes($content))

Write-Host "Fixed!"
