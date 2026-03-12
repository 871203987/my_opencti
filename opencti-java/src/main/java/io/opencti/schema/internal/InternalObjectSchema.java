package io.opencti.schema.internal;

import io.opencti.schema.SchemaTypesDefinition;
import io.opencti.schema.general.SchemaGeneral;

import java.util.*;

/**
 * 内部对象 Schema 定义
 * 重写自: opencti-platform/opencti-graphql/src/schema/internalObject.ts
 *
 * 该文件定义了OpenCTI系统中所有内部对象的类型常量（如Settings、User、Group、Role等），
 * 以及内部对象的类型判断方法。这是区分内部对象和其他类型对象的基础模块。
 */
public final class InternalObjectSchema {

    // 私有构造函数，防止实例化
    private InternalObjectSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 系统配置对象 ====================
    // 原代码: export const ENTITY_TYPE_SETTINGS = 'Settings';
    public static final String ENTITY_TYPE_SETTINGS = "Settings";

    // 原代码: export const ENTITY_TYPE_MIGRATION_STATUS = 'MigrationStatus';
    public static final String ENTITY_TYPE_MIGRATION_STATUS = "MigrationStatus";

    // 原代码: export const ENTITY_TYPE_MIGRATION_REFERENCE = 'MigrationReference';
    public static final String ENTITY_TYPE_MIGRATION_REFERENCE = "MigrationReference";

    // ==================== 规则管理对象 ====================
    // 原代码: export const ENTITY_TYPE_RULE_MANAGER = 'RuleManager';
    public static final String ENTITY_TYPE_RULE_MANAGER = "RuleManager";

    // 原代码: export const ENTITY_TYPE_RULE = 'Rule';
    public static final String ENTITY_TYPE_RULE = "Rule";

    // ==================== 用户权限对象 ====================
    // 原代码: export const ENTITY_TYPE_GROUP = 'Group';
    public static final String ENTITY_TYPE_GROUP = "Group";

    // 原代码: export const ENTITY_TYPE_USER = 'User';
    public static final String ENTITY_TYPE_USER = "User";

    // 原代码: export const ENTITY_TYPE_ROLE = 'Role';
    public static final String ENTITY_TYPE_ROLE = "Role";

    // 原代码: export const ENTITY_TYPE_CAPABILITY = 'Capability';
    public static final String ENTITY_TYPE_CAPABILITY = "Capability";

    // ==================== 连接器对象 ====================
    // 原代码: export const ENTITY_TYPE_CONNECTOR = 'Connector';
    public static final String ENTITY_TYPE_CONNECTOR = "Connector";

    // 原代码: export const ENTITY_TYPE_CONNECTOR_MANAGER = 'ConnectorManager';
    public static final String ENTITY_TYPE_CONNECTOR_MANAGER = "ConnectorManager";

    // ==================== 历史记录对象 ====================
    // 原代码: export const ENTITY_TYPE_HISTORY = 'History';
    public static final String ENTITY_TYPE_HISTORY = "History";

    // 原代码: export const ENTITY_TYPE_PIR_HISTORY = 'PirHistory';
    public static final String ENTITY_TYPE_PIR_HISTORY = "PirHistory";

    // 原代码: export const ENTITY_TYPE_ACTIVITY = 'Activity';
    public static final String ENTITY_TYPE_ACTIVITY = "Activity";

    // ==================== 任务工作对象 ====================
    // 原代码: export const ENTITY_TYPE_WORK = 'work';
    public static final String ENTITY_TYPE_WORK = "work";

    // 原代码: export const ENTITY_TYPE_BACKGROUND_TASK = 'BackgroundTask';
    public static final String ENTITY_TYPE_BACKGROUND_TASK = "BackgroundTask";

    // 原代码: export const ENTITY_TYPE_RETENTION_RULE = 'RetentionRule';
    public static final String ENTITY_TYPE_RETENTION_RULE = "RetentionRule";

    // ==================== 同步集合对象 ====================
    // 原代码: export const ENTITY_TYPE_SYNC = 'Sync';
    public static final String ENTITY_TYPE_SYNC = "Sync";

    // 原代码: export const ENTITY_TYPE_TAXII_COLLECTION = 'TaxiiCollection';
    public static final String ENTITY_TYPE_TAXII_COLLECTION = "TaxiiCollection";

    // 原代码: export const ENTITY_TYPE_INTERNAL_FILE = 'InternalFile';
    public static final String ENTITY_TYPE_INTERNAL_FILE = "InternalFile";

    // 原代码: export const ENTITY_TYPE_FEED = 'Feed';
    public static final String ENTITY_TYPE_FEED = "Feed";

    // 原代码: export const ENTITY_TYPE_STREAM_COLLECTION = 'StreamCollection';
    public static final String ENTITY_TYPE_STREAM_COLLECTION = "StreamCollection";

    // ==================== 状态主题对象 ====================
    // 原代码: export const ENTITY_TYPE_STATUS_TEMPLATE = 'StatusTemplate';
    public static final String ENTITY_TYPE_STATUS_TEMPLATE = "StatusTemplate";

    // 原代码: export const ENTITY_TYPE_STATUS = 'Status';
    public static final String ENTITY_TYPE_STATUS = "Status";

    // 原代码: export const ENTITY_TYPE_THEME = 'Theme';
    public static final String ENTITY_TYPE_THEME = "Theme";

    // ==================== 模块相关对象 ====================
    // 原代码: import { ENTITY_TYPE_WORKSPACE } from '../modules/workspace/workspace-types';
    public static final String ENTITY_TYPE_WORKSPACE = "Workspace";

    // 原代码: import { ENTITY_TYPE_PUBLIC_DASHBOARD } from '../modules/publicDashboard/publicDashboard-types';
    public static final String ENTITY_TYPE_PUBLIC_DASHBOARD = "PublicDashboard";

    // 原代码: import { ENTITY_TYPE_DELETE_OPERATION } from '../modules/deleteOperation/deleteOperation-types';
    public static final String ENTITY_TYPE_DELETE_OPERATION = "DeleteOperation";

    // 原代码: import { ENTITY_TYPE_DRAFT_WORKSPACE } from '../modules/draftWorkspace/draftWorkspace-types';
    public static final String ENTITY_TYPE_DRAFT_WORKSPACE = "DraftWorkspace";

    // 原代码: import { ENTITY_TYPE_EXCLUSION_LIST } from '../modules/exclusionList/exclusionList-types';
    public static final String ENTITY_TYPE_EXCLUSION_LIST = "ExclusionList";

    // 原代码: import { ENTITY_TYPE_FINTEL_TEMPLATE } from '../modules/fintelTemplate/fintelTemplate-types';
    public static final String ENTITY_TYPE_FINTEL_TEMPLATE = "FintelTemplate";

    // 原代码: import { ENTITY_TYPE_SAVED_FILTER } from '../modules/savedFilter/savedFilter-types';
    public static final String ENTITY_TYPE_SAVED_FILTER = "SavedFilter";

    // 原代码: import { ENTITY_TYPE_PIR } from '../modules/pir/pir-types';
    public static final String ENTITY_TYPE_PIR = "Pir";

    // 原代码: import { ENTITY_TYPE_FINTEL_DESIGN } from '../modules/fintelDesign/fintelDesign-types';
    public static final String ENTITY_TYPE_FINTEL_DESIGN = "FintelDesign";

    // 原代码: import { ENTITY_TYPE_EMAIL_TEMPLATE } from '../modules/emailTemplate/emailTemplate-types';
    public static final String ENTITY_TYPE_EMAIL_TEMPLATE = "EmailTemplate";

    // 原代码: import { ENTITY_TYPE_FORM } from '../modules/form/form-types';
    public static final String ENTITY_TYPE_FORM = "Form";

    // ==================== 内部对象分类列表 ====================
    // 原代码: const DATED_INTERNAL_OBJECTS = [...]
    public static final Set<String> DATED_INTERNAL_OBJECTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_SETTINGS,
            ENTITY_TYPE_GROUP,
            ENTITY_TYPE_USER,
            ENTITY_TYPE_ROLE,
            ENTITY_TYPE_CAPABILITY,
            ENTITY_TYPE_CONNECTOR,
            ENTITY_TYPE_WORKSPACE,
            ENTITY_TYPE_SYNC,
            ENTITY_TYPE_PUBLIC_DASHBOARD,
            ENTITY_TYPE_DELETE_OPERATION,
            ENTITY_TYPE_DRAFT_WORKSPACE,
            ENTITY_TYPE_EXCLUSION_LIST,
            ENTITY_TYPE_SAVED_FILTER,
            ENTITY_TYPE_PIR,
            ENTITY_TYPE_FORM,
            ENTITY_TYPE_THEME
    )));

    // 原代码: const INTERNAL_OBJECTS = [...]
    public static final Set<String> INTERNAL_OBJECTS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            ENTITY_TYPE_SETTINGS,
            ENTITY_TYPE_TAXII_COLLECTION,
            ENTITY_TYPE_FEED,
            ENTITY_TYPE_STREAM_COLLECTION,
            ENTITY_TYPE_STATUS_TEMPLATE,
            ENTITY_TYPE_STATUS,
            ENTITY_TYPE_BACKGROUND_TASK,
            ENTITY_TYPE_RETENTION_RULE,
            ENTITY_TYPE_SYNC,
            ENTITY_TYPE_MIGRATION_STATUS,
            ENTITY_TYPE_MIGRATION_REFERENCE,
            ENTITY_TYPE_GROUP,
            ENTITY_TYPE_USER,
            ENTITY_TYPE_ROLE,
            ENTITY_TYPE_RULE,
            ENTITY_TYPE_RULE_MANAGER,
            ENTITY_TYPE_CAPABILITY,
            ENTITY_TYPE_CONNECTOR,
            ENTITY_TYPE_CONNECTOR_MANAGER,
            ENTITY_TYPE_WORKSPACE,
            ENTITY_TYPE_PUBLIC_DASHBOARD,
            ENTITY_TYPE_HISTORY,
            ENTITY_TYPE_PIR_HISTORY,
            ENTITY_TYPE_ACTIVITY,
            ENTITY_TYPE_INTERNAL_FILE,
            ENTITY_TYPE_WORK,
            ENTITY_TYPE_DRAFT_WORKSPACE,
            ENTITY_TYPE_EXCLUSION_LIST,
            ENTITY_TYPE_FINTEL_TEMPLATE,
            ENTITY_TYPE_SAVED_FILTER,
            ENTITY_TYPE_PIR,
            ENTITY_TYPE_FINTEL_DESIGN,
            ENTITY_TYPE_THEME,
            ENTITY_TYPE_EMAIL_TEMPLATE,
            ENTITY_TYPE_FORM
    )));

    // 原代码: const HISTORY_OBJECTS = [ENTITY_TYPE_WORK];
    public static final Set<String> HISTORY_OBJECTS = Collections.unmodifiableSet(new HashSet<>(Collections.singletonList(
            ENTITY_TYPE_WORK
    )));

    // ==================== 类型判断方法 ====================

    /**
     * 判断是否为内部对象
     * 原代码: export const isInternalObject = (type: string) => schemaTypesDefinition.isTypeIncludedIn(type, ABSTRACT_INTERNAL_OBJECT) || type === ABSTRACT_INTERNAL_OBJECT;
     *
     * @param type 类型
     * @return 如果是内部对象返回 true，否则返回 false
     */
    public static boolean isInternalObject(String type) {
        return type != null && (SchemaTypesDefinition.isTypeIncludedIn(type, SchemaGeneral.ABSTRACT_INTERNAL_OBJECT)
                || type.equals(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT));
    }

    /**
     * 判断是否为带日期的内部对象
     * 原代码: export const isDatedInternalObject = (type: string) => DATED_INTERNAL_OBJECTS.includes(type);
     *
     * @param type 类型
     * @return 如果是带日期的内部对象返回 true，否则返回 false
     */
    public static boolean isDatedInternalObject(String type) {
        return type != null && DATED_INTERNAL_OBJECTS.contains(type);
    }

    /**
     * 判断是否为历史对象
     * 原代码: export const isHistoryObject = (type: string) => HISTORY_OBJECTS.includes(type);
     *
     * @param type 类型
     * @return 如果是历史对象返回 true，否则返回 false
     */
    public static boolean isHistoryObject(String type) {
        return type != null && HISTORY_OBJECTS.contains(type);
    }

    // ==================== 静态代码块：类型注册 ====================
    static {
        // 原代码: schemaTypesDefinition.register(ABSTRACT_INTERNAL_OBJECT, INTERNAL_OBJECTS);
        SchemaTypesDefinition.register(SchemaGeneral.ABSTRACT_INTERNAL_OBJECT, INTERNAL_OBJECTS);
    }
}
