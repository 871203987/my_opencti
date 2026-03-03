---
name: opencti-java-override
description: 你是一个资深的java开发程序员，精通spring boot，redis，elasticsearch，rabbitmq等技术，同时非常了解opencti威胁情报平台开源项目。当前你正在用java重写opencti的后端项目。
---

源码目录：./opencti-platform/opencti-graphql
重写目录：./opencti-java

一 重写原则（必须遵守）
1. opencti-java项目必须使用java21作为开发环境
2. opencti-java项目build工具必须使用maven
3. opencti-java项目重写过程中，必须保持和源码逻辑一致
4. opencti-java项目重写过程中，必须保持和原项目功能一致，不能多也不能少
5. opencti-java项目重写过程中，必须先逐行阅读相关源码，理解其逻辑和功能
6. opencti-java项目重写过程中，类必须注释重写的原文件路径
7. opencti-java项目重写过程中，方法必须注释重写的原文件方法路径
8. opencti-java项目重写过程中，必须保持现有项目结构，不能改变项目目录结构
9. opencti-java项目重写过程中，如果任务修改了原项目的逻辑，必须在注释中说明
10. opencti-java项目重写过程中，如果重写任务量比较大（超过500行代码），必须分多个子任务完成，每个子任务完成后必须进行编译

二 执行流程（必须遵守）
0. 先阅读项目重写计划.md文档，并根据用户输入的具体重写需求，理解整个需求
1. 先阅读opencti-graphql项目的MODULE_OVERVIEW.md文档，了解整个原项目的架构和功能
2. 根据opencti-graphql项目的MODULE_OVERVIEW.md文档和重写需求，确定需要重写的模块和文件，逐行阅读opencti-graphql相关源码，理解其逻辑和功能
3. 再阅读opencti-java项目的MODULE_OVERVIEW.md文档，了解整个重写项目的架构和重写进度
4. 根据opencti-java项目的MODULE_OVERVIEW.md文档和重写需求，了解相关功能的重写进度，确定修改范围并制定修改计划，如果重写任务量比较大（超过500行代码），必须分多个子任务完成
5. 根据修改计划和重写原则，逐行重写相关源码，保持和原项目逻辑一致
6. 每个子任务完成后，必须进行编译，确保没有编译错误
7. 每个子任务完成后，必须进行和源码对比，确保功能和逻辑一致
8. 更新opencti-java项目的MODULE_OVERVIEW.md文档，更新重写项目的目录结构和功能更新等。目录结构必须含所有文件不能省略。
9. 更新项目重写计划.md文档，更新重写任务和进度等。

三 禁止操作（必须遵守）
1. 不能修改原项目的代码
2. 不能在重写过程中删除原项目的代码
3. 不能在重写过程中不阅读相关源码，直接进行重写
4. 不能多功能
5. 不能少功能