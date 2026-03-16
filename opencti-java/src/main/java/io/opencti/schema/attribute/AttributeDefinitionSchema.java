package io.opencti.schema.attribute;

/**
 * 属性定义 Schema
 * 重写自: opencti-platform/opencti-graphql/src/schema/attribute-definition.ts
 *
 * 该文件定义了OpenCTI中实体属性的类型体系，包括属性类型、格式、各种属性定义类，
 * 以及全局属性和STIX标准属性定义。
 */
public final class AttributeDefinitionSchema {

    private AttributeDefinitionSchema() {
        throw new AssertionError("Utility class should not be instantiated");
    }

    // ==================== 属性类型枚举 ====================
    // 原代码: export type AttrType = 'string' | 'date' | 'numeric' | 'boolean' | 'object' | 'ref';
    public enum AttrType {
        STRING,     // 字符串类型
        DATE,       // 日期类型
        NUMERIC,    // 数值类型
        BOOLEAN,    // 布尔类型
        OBJECT,     // 对象类型
        REF         // 引用类型
    }

    // ==================== Mandatory类型枚举 ====================
    // 原代码: export type MandatoryType = 'internal' | 'external' | 'customizable' | 'no';
    // internal = 内部强制
    // external = 外部强制
    // customizable = 可定制
    // no = 不可能在动态配置中改为强制
    public enum MandatoryType {
        INTERNAL,       // 内部强制
        EXTERNAL,       // 外部强制
        CUSTOMIZABLE,   // 可定制
        NO              // 不可能改为强制
    }

    // ==================== 字符串格式枚举 ====================
    // 原代码: export const shortStringFormats = ['id', 'short', 'enum', 'vocabulary'];
    // 原代码: export const longStringFormats = ['text', 'json'];
    public enum AttributeFormat {
        ID,         // ID格式
        SHORT,      // 短字符串
        TEXT,       // 长文本
        ENUM,       // 枚举
        VOCABULARY, // 词汇表
        JSON        // JSON格式
    }

    // ==================== 基础属性定义接口 ====================
    // 原代码: type BasicDefinition = { ... }
    public interface AttributeDefinition {
        String getName();           // 数据库中的名称
        String getLabel();          // 前端显示标签
        String getDescription();    // 属性描述
        AttrType getType();         // 属性类型
        boolean isMultiple();       // 是否支持多值
        MandatoryType getMandatoryType(); // 是否强制
        boolean isUpsert();         // 是否支持upsert
        boolean isUpsertForceReplace(); // 多值时是否强制替换
        boolean isFilterable();     // 是否可作为过滤键
        boolean isEditDefault();    // 编辑默认值
        Boolean getUpdate();        // 是否可更新（null = true）
        String getFeatureFlag();    // 特性标志
    }

    // ==================== 抽象基础属性定义类 ====================
    public static abstract class BaseAttributeDefinition implements AttributeDefinition {
        protected final String name;
        protected final String label;
        protected String description;
        protected final AttrType type;
        protected final boolean multiple;
        protected final MandatoryType mandatoryType;
        protected boolean upsert;
        protected boolean upsertForceReplace;
        protected boolean filterable;
        protected boolean editDefault;
        protected Boolean update;
        protected String featureFlag;

        protected BaseAttributeDefinition(String name, String label, AttrType type,
                                          boolean multiple, MandatoryType mandatoryType) {
            this.name = name;
            this.label = label;
            this.type = type;
            this.multiple = multiple;
            this.mandatoryType = mandatoryType;
            this.upsert = true;
            this.upsertForceReplace = false;
            this.filterable = true;
            this.editDefault = false;
            this.update = null; // null means true
        }

        @Override
        public String getName() { return name; }

        @Override
        public String getLabel() { return label; }

        @Override
        public String getDescription() { return description; }

        public void setDescription(String description) { this.description = description; }

        @Override
        public AttrType getType() { return type; }

        @Override
        public boolean isMultiple() { return multiple; }

        @Override
        public MandatoryType getMandatoryType() { return mandatoryType; }

        @Override
        public boolean isUpsert() { return upsert; }

        public void setUpsert(boolean upsert) { this.upsert = upsert; }

        @Override
        public boolean isUpsertForceReplace() { return upsertForceReplace; }

        public void setUpsertForceReplace(boolean upsertForceReplace) { this.upsertForceReplace = upsertForceReplace; }

        @Override
        public boolean isFilterable() { return filterable; }

        public void setFilterable(boolean filterable) { this.filterable = filterable; }

        @Override
        public boolean isEditDefault() { return editDefault; }

        public void setEditDefault(boolean editDefault) { this.editDefault = editDefault; }

        @Override
        public Boolean getUpdate() { return update; }

        public void setUpdate(Boolean update) { this.update = update; }

        @Override
        public String getFeatureFlag() { return featureFlag; }

        public void setFeatureFlag(String featureFlag) { this.featureFlag = featureFlag; }
    }

    // ==================== 日期属性 ====================
    // 原代码: export type DateAttribute = { type: 'date' } & BasicDefinition;
    public static class DateAttribute extends BaseAttributeDefinition {
        public DateAttribute(String name, String label, MandatoryType mandatoryType) {
            super(name, label, AttrType.DATE, false, mandatoryType);
        }

        public DateAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple) {
            super(name, label, AttrType.DATE, multiple, mandatoryType);
        }
    }

    // ==================== 布尔属性 ====================
    // 原代码: export type BooleanAttribute = { type: 'boolean' } & BasicDefinition;
    public static class BooleanAttribute extends BaseAttributeDefinition {
        public BooleanAttribute(String name, String label, MandatoryType mandatoryType) {
            super(name, label, AttrType.BOOLEAN, false, mandatoryType);
        }
    }

    // ==================== 数值属性 ====================
    // 原代码: export type NumericAttribute = { type: 'numeric'; precision: 'integer' | 'long' | 'float'; scalable?: boolean } & BasicDefinition;
    public enum NumericPrecision {
        INTEGER, LONG, FLOAT
    }

    public static class NumericAttribute extends BaseAttributeDefinition {
        private final NumericPrecision precision;
        private boolean scalable;

        public NumericAttribute(String name, String label, MandatoryType mandatoryType, NumericPrecision precision) {
            super(name, label, AttrType.NUMERIC, false, mandatoryType);
            this.precision = precision;
            this.scalable = false;
        }

        public NumericPrecision getPrecision() { return precision; }

        public boolean isScalable() { return scalable; }

        public void setScalable(boolean scalable) { this.scalable = scalable; }
    }

    // ==================== ID属性 ====================
    // 原代码: export type IdAttribute = { type: 'string'; format: 'id'; entityTypes: string[] } & BasicDefinition;
    public static class IdAttribute extends BaseAttributeDefinition {
        private String[] entityTypes;

        public IdAttribute(String name, String label, MandatoryType mandatoryType, String[] entityTypes) {
            super(name, label, AttrType.STRING, false, mandatoryType);
            this.entityTypes = entityTypes;
        }

        public IdAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple, String[] entityTypes) {
            super(name, label, AttrType.STRING, multiple, mandatoryType);
            this.entityTypes = entityTypes;
        }

        public String[] getEntityTypes() { return entityTypes; }

        public void setEntityTypes(String[] entityTypes) { this.entityTypes = entityTypes; }

        public AttributeFormat getFormat() { return AttributeFormat.ID; }
    }

    // ==================== 文本属性 ====================
    // 原代码: export type TextAttribute = { type: 'string'; format: 'short' | 'text' } & BasicDefinition;
    public static class TextAttribute extends BaseAttributeDefinition {
        private final AttributeFormat format;

        public TextAttribute(String name, String label, MandatoryType mandatoryType, AttributeFormat format) {
            super(name, label, AttrType.STRING, false, mandatoryType);
            this.format = format;
        }

        public TextAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple, AttributeFormat format) {
            super(name, label, AttrType.STRING, multiple, mandatoryType);
            this.format = format;
        }

        public AttributeFormat getFormat() { return format; }
    }

    // ==================== 枚举属性 ====================
    // 原代码: export type EnumAttribute = { type: 'string'; format: 'enum'; values: string[] } & BasicDefinition;
    public static class EnumAttribute extends BaseAttributeDefinition {
        private final String[] values;

        public EnumAttribute(String name, String label, MandatoryType mandatoryType, String[] values) {
            super(name, label, AttrType.STRING, false, mandatoryType);
            this.values = values;
        }

        public String[] getValues() { return values; }

        public AttributeFormat getFormat() { return AttributeFormat.ENUM; }
    }

    // ==================== 词汇表属性 ====================
    // 原代码: export type VocabAttribute = { type: 'string'; format: 'vocabulary'; vocabularyCategory: string } & BasicDefinition;
    public static class VocabAttribute extends BaseAttributeDefinition {
        private final String vocabularyCategory;

        public VocabAttribute(String name, String label, MandatoryType mandatoryType, String vocabularyCategory) {
            super(name, label, AttrType.STRING, false, mandatoryType);
            this.vocabularyCategory = vocabularyCategory;
        }

        public String getVocabularyCategory() { return vocabularyCategory; }

        public AttributeFormat getFormat() { return AttributeFormat.VOCABULARY; }
    }

    // ==================== JSON属性 ====================
    // 原代码: export type JsonAttribute = { type: 'string'; format: 'json'; multiple: false; schemaDef?: Record<string, any> } & BasicDefinition;
    public static class JsonAttribute extends BaseAttributeDefinition {
        public JsonAttribute(String name, String label, MandatoryType mandatoryType) {
            super(name, label, AttrType.STRING, false, mandatoryType);
        }

        public AttributeFormat getFormat() { return AttributeFormat.JSON; }
    }

    // ==================== 映射定义 ====================
    // 原代码: export type MappingDefinition = AttributeDefinition & { associatedFilterKeys?: { key: string; label: string }[]; }
    public static class MappingDefinition {
        public final String name;
        public final String label;
        public final AttrType type;
        public AttributeFormat format;
        public String[] values; // for enum
        public String vocabularyCategory; // for vocabulary
        public String[] entityTypes; // for id
        public NumericPrecision precision; // for numeric
        public boolean multiple;
        public MandatoryType mandatoryType;
        public boolean upsert;
        public boolean isFilterable;
        public boolean editDefault;

        public MappingDefinition(String name, String label, AttrType type, AttributeFormat format,
                                 MandatoryType mandatoryType, boolean editDefault, boolean multiple,
                                 boolean upsert, boolean isFilterable) {
            this.name = name;
            this.label = label;
            this.type = type;
            this.format = format;
            this.mandatoryType = mandatoryType;
            this.editDefault = editDefault;
            this.multiple = multiple;
            this.upsert = upsert;
            this.isFilterable = isFilterable;
        }
    }

    // ==================== 排序配置 ====================
    // 原代码: sortBy?: { path: string; type: string; }
    public static class SortBy {
        public final String path;
        public final String type;

        public SortBy(String path, String type) {
            this.path = path;
            this.type = type;
        }
    }

    // ==================== 复杂属性定义（对象类型） ====================
    // 原代码: export type FlatObjectAttribute = { type: 'object'; format: 'flat' } & BasicDefinition;
    public static class FlatObjectAttribute extends BaseAttributeDefinition {
        public FlatObjectAttribute(String name, String label, MandatoryType mandatoryType) {
            super(name, label, AttrType.OBJECT, false, mandatoryType);
        }
    }

    // 原代码: export type ObjectAttribute = { type: 'object'; format: 'standard' } & BasicObjectDefinition;
    public static class ObjectAttribute extends BaseAttributeDefinition {
        private MappingDefinition[] mappings;
        private SortBy sortBy;

        public ObjectAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple,
                               MappingDefinition[] mappings) {
            super(name, label, AttrType.OBJECT, multiple, mandatoryType);
            this.mappings = mappings;
        }

        public MappingDefinition[] getMappings() { return mappings; }

        public void setMappings(MappingDefinition[] mappings) { this.mappings = mappings; }

        public SortBy getSortBy() { return sortBy; }

        public void setSortBy(SortBy sortBy) { this.sortBy = sortBy; }
    }

    // 原代码: export type NestedObjectAttribute = { type: 'object'; format: 'nested' } & BasicObjectDefinition;
    public static class NestedObjectAttribute extends BaseAttributeDefinition {
        private MappingDefinition[] mappings;
        private SortBy sortBy;

        public NestedObjectAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple,
                                     MappingDefinition[] mappings) {
            super(name, label, AttrType.OBJECT, multiple, mandatoryType);
            this.mappings = mappings;
        }

        public MappingDefinition[] getMappings() { return mappings; }

        public void setMappings(MappingDefinition[] mappings) { this.mappings = mappings; }

        public SortBy getSortBy() { return sortBy; }

        public void setSortBy(SortBy sortBy) { this.sortBy = sortBy; }
    }

    // ==================== 引用属性 ====================
    // 原代码: export type RefAttribute = { type: 'ref'; databaseName: string; stixName: string; isRefExistingForTypes: Checker; datable?: boolean; toTypes: string[] } & BasicDefinition;
    public interface Checker {
        boolean check(String fromType, String toType);
    }

    public static class RefAttribute extends BaseAttributeDefinition {
        private final String databaseName;
        private final String stixName;
        private final Checker isRefExistingForTypes;
        private boolean datable;
        private final String[] toTypes;

        public RefAttribute(String name, String label, MandatoryType mandatoryType, boolean multiple,
                            String databaseName, String stixName, Checker isRefExistingForTypes, String[] toTypes) {
            super(name, label, AttrType.REF, multiple, mandatoryType);
            this.databaseName = databaseName;
            this.stixName = stixName;
            this.isRefExistingForTypes = isRefExistingForTypes;
            this.toTypes = toTypes;
            this.datable = false;
        }

        public String getDatabaseName() { return databaseName; }

        public String getStixName() { return stixName; }

        public Checker getIsRefExistingForTypes() { return isRefExistingForTypes; }

        public boolean isDatable() { return datable; }

        public void setDatable(boolean datable) { this.datable = datable; }

        public String[] getToTypes() { return toTypes; }
    }

    // ==================== 类型守卫方法 ====================
    // 原代码: export const isNumericAttribute = (attribute: AttributeDefinition): attribute is NumericAttribute => attribute.type === 'numeric';
    public static boolean isNumericAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.NUMERIC;
    }

    // 原代码: export const isDateAttribute = (attribute: AttributeDefinition): attribute is DateAttribute => attribute.type === 'date';
    public static boolean isDateAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.DATE;
    }

    // 原代码: export const isBooleanAttribute = (attribute: AttributeDefinition): attribute is BooleanAttribute => attribute.type === 'boolean';
    public static boolean isBooleanAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.BOOLEAN;
    }

    // 原代码: export const isStringAttribute = (attribute: AttributeDefinition): attribute is StringAttribute => attribute.type === 'string';
    public static boolean isStringAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.STRING;
    }

    // 原代码: export const isComplexAttribute = (attribute: AttributeDefinition): attribute is ComplexAttribute => attribute.type === 'object';
    public static boolean isComplexAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.OBJECT;
    }

    // 原代码: export const isRefAttribute = (attribute: AttributeDefinition): attribute is RefAttribute => attribute.type === 'ref';
    public static boolean isRefAttribute(AttributeDefinition attribute) {
        return attribute != null && attribute.getType() == AttrType.REF;
    }

    // ==================== 全局属性定义 ====================

    // 原代码: export const id: IdAttribute = { ... }
    public static final IdAttribute ATTR_ID = new IdAttribute(
            "id",
            "ID",
            MandatoryType.INTERNAL,
            new String[]{}
    );

    // 原代码: export const internalId: IdAttribute = { ... }
    public static final IdAttribute ATTR_INTERNAL_ID = new IdAttribute(
            "internal_id",
            "Internal ID",
            MandatoryType.INTERNAL,
            new String[]{}
    );
    static {
        ATTR_INTERNAL_ID.setDescription("Internal technical id");
    }

    // 原代码: export const standardId: IdAttribute = { ... }
    public static final IdAttribute ATTR_STANDARD_ID = new IdAttribute(
            "standard_id",
            "Standard ID",
            MandatoryType.INTERNAL,
            new String[]{}
    );
    static {
        ATTR_STANDARD_ID.setDescription("Standard STIX id");
    }

    // 原代码: export const xOpenctiStixIds: IdAttribute = { ... }
    public static final IdAttribute ATTR_X_OPENCTI_STIX_IDS = new IdAttribute(
            "x_opencti_stix_ids",
            "Other STIX IDs",
            MandatoryType.NO,
            true,
            new String[]{}
    );
    static {
        ATTR_X_OPENCTI_STIX_IDS.setDescription("Alternative STIX IDs used to identify this entity");
        ATTR_X_OPENCTI_STIX_IDS.setUpsert(true);
    }

    // 原代码: export const xOpenctiAliases: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_ALIASES = new TextAttribute(
            "x_opencti_aliases",
            "Aliases",
            MandatoryType.NO,
            true,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_ALIASES.setDescription("Alternative names used to identify this entity");
    }

    // 原代码: export const xOpenctiInternalId: IdAttribute = { ... }
    public static final IdAttribute ATTR_X_OPENCTI_INTERNAL_ID = new IdAttribute(
            "x_opencti_internal_id",
            "Internal ID",
            MandatoryType.INTERNAL,
            new String[]{}
    );
    static {
        ATTR_X_OPENCTI_INTERNAL_ID.setDescription("Internal technical id");
    }

    // 原代码: export const iAliasedIds: IdAttribute = { ... }
    public static final IdAttribute ATTR_I_ALIASED_IDS = new IdAttribute(
            "i_aliased_ids",
            "Aliased IDs",
            MandatoryType.INTERNAL,
            true,
            new String[]{}
    );
    static {
        ATTR_I_ALIASED_IDS.setDescription("Internal IDs of aliased entities");
    }

    // 原代码: export const createdAt: DateAttribute = { ... }
    public static final DateAttribute ATTR_CREATED_AT = new DateAttribute(
            "created_at",
            "Platform creation date",
            MandatoryType.INTERNAL
    );
    static {
        ATTR_CREATED_AT.setDescription("Technical date (date of creation of the entity in the platform)");
    }

    // 原代码: export const updatedAt: DateAttribute = { ... }
    public static final DateAttribute ATTR_UPDATED_AT = new DateAttribute(
            "updated_at",
            "Platform modification date",
            MandatoryType.INTERNAL
    );
    static {
        ATTR_UPDATED_AT.setDescription("Technical date (date of modification of the entity in the platform)");
    }

    // 原代码: export const creators: RefAttribute = { ... }
    public static final RefAttribute ATTR_CREATORS = new RefAttribute(
            "creator_id",
            "Authors",
            MandatoryType.NO,
            true,
            "creator_id",
            "creator_ids",
            (fromType, toType) -> true,
            new String[]{}
    );
    static {
        ATTR_CREATORS.setDescription("Creators of the entity");
    }

    // 原代码: export const iAttributes: JsonAttribute = { ... }
    public static final JsonAttribute ATTR_I_ATTRIBUTES = new JsonAttribute(
            "i_attributes",
            "Attributes",
            MandatoryType.INTERNAL
    );
    static {
        ATTR_I_ATTRIBUTES.setDescription("Attributes of the entity");
    }

    // 原代码: export const draftIds: IdAttribute = { ... }
    public static final IdAttribute ATTR_DRAFT_IDS = new IdAttribute(
            "draft_ids",
            "Draft IDs",
            MandatoryType.NO,
            true,
            new String[]{}
    );
    static {
        ATTR_DRAFT_IDS.setDescription("Draft IDs of the entity");
    }

    // 原代码: export const refreshedAt: DateAttribute = { ... }
    public static final DateAttribute ATTR_REFRESHED_AT = new DateAttribute(
            "refreshed_at",
            "Refreshed date",
            MandatoryType.INTERNAL
    );
    static {
        ATTR_REFRESHED_AT.setDescription("Technical date (date of refresh of the entity in the platform)");
    }

    // 原代码: export const lastEventId: TextAttribute = { ... }
    public static final TextAttribute ATTR_LAST_EVENT_ID = new TextAttribute(
            "last_event_id",
            "Last event ID",
            MandatoryType.INTERNAL,
            AttributeFormat.SHORT
    );
    static {
        ATTR_LAST_EVENT_ID.setDescription("Last event ID");
    }

    // 原代码: export const files: JsonAttribute = { ... }
    public static final JsonAttribute ATTR_FILES = new JsonAttribute(
            "files",
            "Files",
            MandatoryType.NO
    );
    static {
        ATTR_FILES.setDescription("Files associated with the entity");
    }

    // ==================== STIX标准属性定义 ====================

    // 原代码: export const aliases: TextAttribute = { ... }
    public static final TextAttribute ATTR_ALIASES = new TextAttribute(
            "aliases",
            "Aliases",
            MandatoryType.NO,
            true,
            AttributeFormat.SHORT
    );
    static {
        ATTR_ALIASES.setDescription("Alternative names used to identify this entity");
    }

    // 原代码: export const created: DateAttribute = { ... }
    public static final DateAttribute ATTR_CREATED = new DateAttribute(
            "created",
            "Creation date",
            MandatoryType.EXTERNAL
    );
    static {
        ATTR_CREATED.setDescription("Creation date of the entity");
    }

    // 原代码: export const modified: DateAttribute = { ... }
    public static final DateAttribute ATTR_MODIFIED = new DateAttribute(
            "modified",
            "Modification date",
            MandatoryType.EXTERNAL
    );
    static {
        ATTR_MODIFIED.setDescription("Modification date of the entity");
    }

    // 原代码: export const revoked: BooleanAttribute = { ... }
    public static final BooleanAttribute ATTR_REVOKED = new BooleanAttribute(
            "revoked",
            "Revoked",
            MandatoryType.NO
    );
    static {
        ATTR_REVOKED.setDescription("Whether the entity is revoked");
    }

    // 原代码: export const confidence: NumericAttribute = { ... }
    public static final NumericAttribute ATTR_CONFIDENCE = new NumericAttribute(
            "confidence",
            "Confidence",
            MandatoryType.CUSTOMIZABLE,
            NumericPrecision.INTEGER
    );
    static {
        ATTR_CONFIDENCE.setDescription("Confidence level of the entity");
    }

    // 原代码: export const lang: TextAttribute = { ... }
    public static final TextAttribute ATTR_LANG = new TextAttribute(
            "lang",
            "Language",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_LANG.setDescription("Language of the entity");
    }

    // 原代码: export const xOpenctiReliability: VocabAttribute = { ... }
    public static final VocabAttribute ATTR_X_OPENCTI_RELIABILITY = new VocabAttribute(
            "x_opencti_reliability",
            "Reliability",
            MandatoryType.NO,
            "reliability"
    );
    static {
        ATTR_X_OPENCTI_RELIABILITY.setDescription("Reliability of the entity");
    }

    // 原代码: export const identityClass: EnumAttribute = { ... }
    public static final EnumAttribute ATTR_IDENTITY_CLASS = new EnumAttribute(
            "identity_class",
            "Identity class",
            MandatoryType.NO,
            new String[]{"individual", "group", "organization", "class", "unknown"}
    );
    static {
        ATTR_IDENTITY_CLASS.setDescription("Class of the identity");
    }

    // 原代码: export const xOpenctiLocationType: EnumAttribute = { ... }
    public static final EnumAttribute ATTR_X_OPENCTI_LOCATION_TYPE = new EnumAttribute(
            "x_opencti_location_type",
            "Location type",
            MandatoryType.NO,
            new String[]{"Administrative-Area", "City", "Country", "Position", "Region"}
    );
    static {
        ATTR_X_OPENCTI_LOCATION_TYPE.setDescription("Type of the location");
    }

    // 原代码: export const xOpenctiFirstname: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_FIRSTNAME = new TextAttribute(
            "x_opencti_firstname",
            "Firstname",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_FIRSTNAME.setDescription("Firstname of the user");
    }

    // 原代码: export const xOpenctiLastname: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_LASTNAME = new TextAttribute(
            "x_opencti_lastname",
            "Lastname",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_LASTNAME.setDescription("Lastname of the user");
    }

    // 原代码: export const xOpenctiOrganizationType: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_ORGANIZATION_TYPE = new TextAttribute(
            "x_opencti_organization_type",
            "Organization type",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_ORGANIZATION_TYPE.setDescription("Type of the organization");
    }

    // 原代码: export const xOpenctiDescription: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_DESCRIPTION = new TextAttribute(
            "x_opencti_description",
            "Description",
            MandatoryType.NO,
            AttributeFormat.TEXT
    );
    static {
        ATTR_X_OPENCTI_DESCRIPTION.setDescription("Description of the entity");
    }

    // 原代码: export const xOpenctiScore: NumericAttribute = { ... }
    public static final NumericAttribute ATTR_X_OPENCTI_SCORE = new NumericAttribute(
            "x_opencti_score",
            "Score",
            MandatoryType.NO,
            NumericPrecision.INTEGER
    );
    static {
        ATTR_X_OPENCTI_SCORE.setDescription("Score of the entity");
    }

    // 原代码: export const xOpenctiDetection: BooleanAttribute = { ... }
    public static final BooleanAttribute ATTR_X_OPENCTI_DETECTION = new BooleanAttribute(
            "x_opencti_detection",
            "Detection",
            MandatoryType.NO
    );
    static {
        ATTR_X_OPENCTI_DETECTION.setDescription("Whether the indicator is for detection");
    }

    // 原代码: export const xOpenctiMainObservableType: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_MAIN_OBSERVABLE_TYPE = new TextAttribute(
            "x_opencti_main_observable_type",
            "Main observable type",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_MAIN_OBSERVABLE_TYPE.setDescription("Main observable type of the indicator");
    }

    // 原代码: export const xOpenctiWorkflowId: IdAttribute = { ... }
    public static final IdAttribute ATTR_X_OPENCTI_WORKFLOW_ID = new IdAttribute(
            "x_opencti_workflow_id",
            "Workflow",
            MandatoryType.NO,
            new String[]{}
    );
    static {
        ATTR_X_OPENCTI_WORKFLOW_ID.setDescription("Workflow status of the entity");
    }

    // 原代码: export const xOpenctiAssigneeIds: IdAttribute = { ... }
    public static final IdAttribute ATTR_X_OPENCTI_ASSIGNEE_IDS = new IdAttribute(
            "x_opencti_assignee_ids",
            "Assignees",
            MandatoryType.NO,
            true,
            new String[]{}
    );
    static {
        ATTR_X_OPENCTI_ASSIGNEE_IDS.setDescription("Assignees of the entity");
    }

    // 原代码: export const xOpenctiParticipantIds: IdAttribute = { ... }
    public static final IdAttribute ATTR_X_OPENCTI_PARTICIPANT_IDS = new IdAttribute(
            "x_opencti_participant_ids",
            "Participants",
            MandatoryType.NO,
            true,
            new String[]{}
    );
    static {
        ATTR_X_OPENCTI_PARTICIPANT_IDS.setDescription("Participants of the entity");
    }

    // 原代码: export const xOpenctiSeverity: VocabAttribute = { ... }
    public static final VocabAttribute ATTR_X_OPENCTI_SEVERITY = new VocabAttribute(
            "x_opencti_severity",
            "Severity",
            MandatoryType.NO,
            "severity"
    );
    static {
        ATTR_X_OPENCTI_SEVERITY.setDescription("Severity of the entity");
    }

    // 原代码: export const xOpenctiPriority: VocabAttribute = { ... }
    public static final VocabAttribute ATTR_X_OPENCTI_PRIORITY = new VocabAttribute(
            "x_opencti_priority",
            "Priority",
            MandatoryType.NO,
            "priority"
    );
    static {
        ATTR_X_OPENCTI_PRIORITY.setDescription("Priority of the entity");
    }

    // 原代码: export const xOpenctiIsDetected: BooleanAttribute = { ... }
    public static final BooleanAttribute ATTR_X_OPENCTI_IS_DETECTED = new BooleanAttribute(
            "x_opencti_is_detected",
            "Is detected",
            MandatoryType.NO
    );
    static {
        ATTR_X_OPENCTI_IS_DETECTED.setDescription("Whether the entity is detected");
    }

    // 原代码: export const xOpenctiCvssBaseScore: NumericAttribute = { ... }
    public static final NumericAttribute ATTR_X_OPENCTI_CVSS_BASE_SCORE = new NumericAttribute(
            "x_opencti_cvss_base_score",
            "CVSS Base Score",
            MandatoryType.NO,
            NumericPrecision.FLOAT
    );
    static {
        ATTR_X_OPENCTI_CVSS_BASE_SCORE.setDescription("CVSS base score of the vulnerability");
    }

    // 原代码: export const xOpenctiCvssBaseSeverity: TextAttribute = { ... }
    public static final TextAttribute ATTR_X_OPENCTI_CVSS_BASE_SEVERITY = new TextAttribute(
            "x_opencti_cvss_base_severity",
            "CVSS Base Severity",
            MandatoryType.NO,
            AttributeFormat.SHORT
    );
    static {
        ATTR_X_OPENCTI_CVSS_BASE_SEVERITY.setDescription("CVSS base severity of the vulnerability");
    }

    // 原代码: export const xOpenctiEpssScore: NumericAttribute = { ... }
    public static final NumericAttribute ATTR_X_OPENCTI_EPSS_SCORE = new NumericAttribute(
            "x_opencti_epss_score",
            "EPSS Score",
            MandatoryType.NO,
            NumericPrecision.FLOAT
    );
    static {
        ATTR_X_OPENCTI_EPSS_SCORE.setDescription("EPSS score of the vulnerability");
    }

    // 原代码: export const xOpenctiEpssPercentile: NumericAttribute = { ... }
    public static final NumericAttribute ATTR_X_OPENCTI_EPSS_PERCENTILE = new NumericAttribute(
            "x_opencti_epss_percentile",
            "EPSS Percentile",
            MandatoryType.NO,
            NumericPrecision.FLOAT
    );
    static {
        ATTR_X_OPENCTI_EPSS_PERCENTILE.setDescription("EPSS percentile of the vulnerability");
    }

    // 原代码: export const xOpenctiCisaKev: BooleanAttribute = { ... }
    public static final BooleanAttribute ATTR_X_OPENCTI_CISA_KEV = new BooleanAttribute(
            "x_opencti_cisa_kev",
            "CISA KEV",
            MandatoryType.NO
    );
    static {
        ATTR_X_OPENCTI_CISA_KEV.setDescription("Whether the vulnerability is in CISA KEV catalog");
    }
}
