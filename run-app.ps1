# 构建classpath并运行应用
$classpathFile = "target/classpath.txt"

# 如果classpath文件不存在，生成它
if (-not (Test-Path $classpathFile)) {
    Write-Host "Generating classpath..."
    $mvnCmd = "mvn"
    $args = @(
        "org.apache.maven.plugins:maven-dependency-plugin:3.6.1:build-classpath",
        "-DincludeScope=runtime",
        "-Dmdep.outputFile=$classpathFile",
        "-q"
    )
    & $mvnCmd $args
}

# 读取classpath
$deps = Get-Content $classpathFile

# 构建完整classpath
$cp = "target/classes;$deps"

Write-Host "Starting OpenCTI Application..."
java -cp "$cp" io.opencti.OpenCTIApplication
