$files = @(
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixUserAgent.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixTrackingNumber.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixText.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixPhoneNumber.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixPersona.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixPaymentCard.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixMediaContent.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixHostname.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixCryptographicKey.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixCryptocurrencyWallet.java',
    'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\types\stix\sco\StixCredential.java'
)

foreach ($file in $files) {
    $content = Get-Content $file -Raw
    
    # Replace field declaration
    $content = $content -replace 'private List<String> objectMarkingRefs;', 'private List<StixId> objectMarkingRefs;'
    
    # Replace getter and setter
    $content = $content -replace 'public List<String> getObjectMarkingRefs\(\)', 'public List<StixId> getObjectMarkingRefs()'
    $content = $content -replace 'public void setObjectMarkingRefs\(List<String> objectMarkingRefs\)', 'public void setObjectMarkingRefs(List<StixId> objectMarkingRefs)'
    
    Set-Content $file $content -NoNewline
    Write-Host "Fixed $file"
}
