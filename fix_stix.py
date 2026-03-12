#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import re

file_path = r'd:\project\open_cti\opencti\opencti-java\src\main\java\io\opencti\database\stix\StixCoreRelationshipsMapping.java'

with open(file_path, 'r', encoding='utf-8', errors='replace') as f:
    content = f.read()

# Fix 1: Line ~993 - split comment and code
old1 = '// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?        if (isStixCyberObservable(toType)) {'
new1 = '''// 濡傛灉鏍稿績鍏崇郴鐨勭洰鏍囩被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        // 杩欎簺鍏崇郴鏄疭TIX瑙勮寖鐨勬墿灞?
        if (isStixCyberObservable(toType)) {'''
content = content.replace(old1, new1)

# Fix 2: Line ~1005 - split comment and code
old2 = '// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {'
new2 = '''// 濡傛灉鏍稿績鍏崇郴鐨勬簮绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
        if (isStixCyberObservable(fromType)) {'''
content = content.replace(old2, new2)

# Fix 3: Line ~1079 - split comment and code
old3 = '// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(toType)) {'
new3 = '''// 濡傛灉鐩爣绫诲瀷鏄疌yber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(toType)) {'''
content = content.replace(old3, new3)

# Fix 4: Line ~1087 - split comment and code
old4 = '// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?        if (isStixCyberObservable(fromType)) {'
new4 = '''// 濡傛灉婧愮被鍨嬫槸Cyber Observable锛屾鏌ユ娊璞＄被鍨嬫槧灏?
            if (isStixCyberObservable(fromType)) {'''
content = content.replace(old4, new4)

with open(file_path, 'w', encoding='utf-8') as f:
    f.write(content)

print("Fixed StixCoreRelationshipsMapping.java!")
