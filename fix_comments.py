import re

file_path = r'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java'

with open(file_path, 'r', encoding='utf-8', errors='replace') as f:
    content = f.read()

# Fix 1: Line ~993 - split comment and code
content = content.replace(
    '// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?        if (isStixCyberObservable(toType)) {',
    '''// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?
        if (isStixCyberObservable(toType)) {'''
)

# Fix 2: Line ~1003 - split comment and code
content = content.replace(
    '// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {',
    '''// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        if (isStixCyberObservable(fromType)) {'''
)

# Fix 3: Line ~1071 - split comment and code
content = content.replace(
    '// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(toType)) {',
    '''// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(toType)) {'''
)

# Fix 4: Line ~1079 - split comment and code
content = content.replace(
    '// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {',
    '''// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(fromType)) {'''
)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed!")
