# Fix BOM characters in Java files
# This script removes BOM (Byte Order Mark) from Java files

$javaFiles = Get-ChildItem -Path "d:\project\open_cti\opencti\opencti-java\src\main\java" -Recurse -Filter "*.java"

foreach ($file in $javaFiles) {
    $content = [System.IO.File]::ReadAllBytes($file.FullName)
    
    # Check if file starts with BOM (EF BB BF)
    if ($content.Length -ge 3 -and $content[0] -eq 0xEF -and $content[1] -eq 0xBB -and $content[2] -eq 0xBF) {
        Write-Host "Fixing BOM in: $($file.FullName)"
        
        # Remove BOM
        $newContent = $content[3..($content.Length-1)]
        [System.IO.File]::WriteAllBytes($file.FullName, $newContent)
    }
}

Write-Host "BOM fix completed!"
