package com.interesting.common.constant;

public interface CommonConstant {
    //    定义权限相关变量名
    public final static String CONTEXT_TOKEN = "Authorization";
    public final static String CONTEXT_USER_ID = "contextUserId";
    public final static String CONTEXT_VISITOR_ID = "contextVisitorId";
    public final static String CONTEXT_OPEN_ID = "contextOpenId";
    public final static String CONTEXT_COMMUNITY_Id = "contextCommunityId";
    public final static String CONTEXT_PHONE = "contextPhone";
    public final static String CONTEXT_USER_ORG_ID = "contextOrgId";
    public final static String CONTEXT_NAME = "contextName";
    public final static String CONTEXT_USER_INFO = "contextInfo";

    //定义相关对应字典值

    /**
     * 正常状态
     */
    public static final Integer STATUS_NORMAL = 0;

    /**
     * 禁用状态
     */
    public static final Integer STATUS_DISABLE = -1;

    /**
     * 删除标志
     */
    public static final Integer IZ_DELETE_1 = 1;

    /**
     * 未删除
     */
    public static final Integer IZ_DELETE_0 = 0;

    /**
     * 系统日志类型： 登录
     */
    public static final int LOG_TYPE_1 = 1;

    /**
     * 系统日志类型： 操作
     */
    public static final int LOG_TYPE_2 = 2;

    /**
     * 操作日志类型： 查询
     */
    public static final int OPERATE_TYPE_1 = 1;

    /**
     * 操作日志类型： 添加
     */
    public static final int OPERATE_TYPE_2 = 2;

    /**
     * 操作日志类型： 更新
     */
    public static final int OPERATE_TYPE_3 = 3;

    /**
     * 操作日志类型： 删除
     */
    public static final int OPERATE_TYPE_4 = 4;

    /**
     * 操作日志类型： 倒入
     */
    public static final int OPERATE_TYPE_5 = 5;

    /**
     * 操作日志类型： 导出
     */
    public static final int OPERATE_TYPE_6 = 6;


    /**
     * {@code 500 Server Error} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_INTERNAL_SERVER_ERROR_500 = 500;
    /**
     * {@code 200 OK} (HTTP/1.0 - RFC 1945)
     */
    public static final Integer SC_OK_200 = 200;

    /**
     * 访问权限认证未通过 510
     */
    public static final Integer SC_JEECG_NO_AUTHZ = 510;
    public static final Integer SC_JEECG_NO_CommunityId = 530;
    /**
     * 游客访问权限未通过 520
     */
    public static final Integer SC_JEECG_VISTION_NO_AUTHZ = 520;

    /**
     * 未登录 510
     */
    public static final Integer SC_JEECG_NO_ = 501;

    /**
     * 登录用户Shiro权限缓存KEY前缀
     */
    public static String PREFIX_USER_SHIRO_CACHE = "shiro:cache:org.jeecg.config.shiro.ShiroRealm.authorizationCache:";
    /**
     * 登录用户Token令牌缓存KEY前缀
     */
    public static final String PREFIX_USER_TOKEN = "prefix_user_token_";
    /**
     * Token缓存时间：3600秒即一小时
     */
    public static final int TOKEN_EXPIRE_TIME = 3600;


    /**
     * 0：一级菜单
     */
    public static final Integer MENU_TYPE_0 = 0;
    /**
     * 1：子菜单
     */
    public static final Integer MENU_TYPE_1 = 1;
    /**
     * 2：按钮权限
     */
    public static final Integer MENU_TYPE_2 = 2;

    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    public static final String MSG_TYPE_UESR = "USER";
    public static final String MSG_TYPE_ALL = "ALL";

    /**
     * 发布状态（0未发布，1已发布，2已撤销）
     */
    public static final String NO_SEND = "0";
    public static final String HAS_SEND = "1";
    public static final String HAS_CANCLE = "2";

    /**
     * 阅读状态（0未读，1已读）
     */
    public static final String HAS_READ_FLAG = "1";
    public static final String NO_READ_FLAG = "0";

    /**
     * 优先级（L低，M中，H高）
     */
    public static final String PRIORITY_L = "L";
    public static final String PRIORITY_M = "M";
    public static final String PRIORITY_H = "H";

    /**
     * 短信模板方式  0 .登录模板、1.注册模板、2.忘记密码模板
     */
    public static final String SMS_TPL_TYPE_0 = "0";
    public static final String SMS_TPL_TYPE_1 = "1";
    public static final String SMS_TPL_TYPE_2 = "2";

    /**
     * 状态(0无效1有效)
     */
    public static final String STATUS_0 = "0";
    public static final String STATUS_1 = "1";

    /**
     * 同步工作流引擎1同步0不同步
     */
    public static final Integer ACT_SYNC_1 = 1;
    public static final Integer ACT_SYNC_0 = 0;

    /**
     * 消息类型1:通知公告2:系统消息
     */
    public static final String MSG_CATEGORY_1 = "1";
    public static final String MSG_CATEGORY_2 = "2";

    /**
     * 是否配置菜单的数据权限 1是0否
     */
    public static final Integer RULE_FLAG_0 = 0;
    public static final Integer RULE_FLAG_1 = 1;

    /**
     * 是否用户已被冻结 1正常(解冻) 2冻结
     */
    public static final Integer USER_UNFREEZE = 1;
    public static final Integer USER_FREEZE = 2;

    /**
     * 字典翻译文本后缀
     */
    public static final String DICT_TEXT_SUFFIX = "_dictText";

    /**
     * 表单设计器主表类型
     */
    public static final Integer DESIGN_FORM_TYPE_MAIN = 1;

    /**
     * 表单设计器子表表类型
     */
    public static final Integer DESIGN_FORM_TYPE_SUB = 2;

    /**
     * 表单设计器URL授权通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_PASSED = 1;

    /**
     * 表单设计器URL授权未通过
     */
    public static final Integer DESIGN_FORM_URL_STATUS_NOT_PASSED = 2;

    /**
     * 表单设计器新增 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_ADD = "add";
    /**
     * 表单设计器修改 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_EDIT = "edit";
    /**
     * 表单设计器详情 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_DETAIL = "detail";
    /**
     * 表单设计器复用数据 Flag
     */
    public static final String DESIGN_FORM_URL_TYPE_REUSE = "reuse";
    /**
     * 表单设计器编辑 Flag （已弃用）
     */
    public static final String DESIGN_FORM_URL_TYPE_VIEW = "view";

    /**
     * online参数值设置（是：Y, 否：N）
     */
    public static final String ONLINE_PARAM_VAL_IS_TURE = "Y";
    public static final String ONLINE_PARAM_VAL_IS_FALSE = "N";

    /**
     * 文件上传类型（本地：local，Minio：minio，阿里云：alioss）
     */
    public static final String UPLOAD_TYPE_LOCAL = "local";
    public static final String UPLOAD_TYPE_OSS = "alioss";

    /**
     * 文档上传自定义桶名称
     */
    public static final String UPLOAD_CUSTOM_BUCKET = "eoafile";
    /**
     * 文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH = "eoafile";
    /**
     * 文件外链接有效天数
     */
    public static final Integer UPLOAD_EFFECTIVE_DAYS = 1;

    /**
     * 员工身份 （1:普通员工  2:上级）
     */
    public static final Integer USER_IDENTITY_1 = 1;
    public static final Integer USER_IDENTITY_2 = 2;

    /**
     * sys_user 表 username 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_SYS_USER_USERNAME = "uniq_sys_user_username";
    /**
     * sys_user 表 work_no 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_SYS_USER_WORK_NO = "uniq_sys_user_work_no";
    /**
     * sys_user 表 phone 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_SYS_USER_PHONE = "uniq_sys_user_phone";
    /**
     * sys_user 表 email 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_SYS_USER_EMAIL = "uniq_sys_user_email";
    /**
     * sys_quartz_job 表 job_class_name 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_JOB_CLASS_NAME = "uniq_job_class_name";
    /**
     * sys_position 表 code 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_CODE = "uniq_code";
    /**
     * sys_role 表 code 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_SYS_ROLE_CODE = "uniq_sys_role_role_code";
    /**
     * sys_depart 表 code 唯一键索引
     */
    public static final String SQL_INDEX_UNIQ_DEPART_ORG_CODE = "uniq_depart_org_code";
    /**
     * 在线聊天 是否为默认分组
     */
    public static final String IM_DEFAULT_GROUP = "1";
    /**
     * 在线聊天 图片文件保存路径
     */
    public static final String IM_UPLOAD_CUSTOM_PATH = "imfile";
    /**
     * 在线聊天 用户状态
     */
    public static final String IM_STATUS_ONLINE = "online";

    /**
     * 在线聊天 SOCKET消息类型
     */
    public static final String IM_SOCKET_TYPE = "chatMessage";

    /**
     * 在线聊天 是否开启默认添加好友 1是 0否
     */
    public static final String IM_DEFAULT_ADD_FRIEND = "1";

    /**
     * 在线聊天 用户好友缓存前缀
     */
    public static final String IM_PREFIX_USER_FRIEND_CACHE = "sys:cache:im:im_prefix_user_friend_";

    /**
     * 考勤补卡业务状态 （1：同意  2：不同意）
     */
    public static final String SIGN_PATCH_BIZ_STATUS_1 = "1";
    public static final String SIGN_PATCH_BIZ_STATUS_2 = "2";

    /**
     * 公文文档上传自定义路径
     */
    public static final String UPLOAD_CUSTOM_PATH_OFFICIAL = "officialdoc";
    /**
     * 公文文档下载自定义路径
     */
    public static final String DOWNLOAD_CUSTOM_PATH_OFFICIAL = "officaldown";

    /**
     * WPS存储值类别(1 code文号 2 text（WPS模板还是公文发文模板）)
     */
    public static final String WPS_TYPE_1 = "1";
    public static final String WPS_TYPE_2 = "2";


    public final static String X_ACCESS_TOKEN = "X-Access-Token";

    /**
     * 多租户 请求头
     */
    public final static String TENANT_ID = "tenant-id";

    /**
     * 微服务读取配置文件属性 服务地址
     */
    public final static String CLOUD_SERVER_KEY = "spring.cloud.nacos.discovery.server-addr";

    /**
     * 第三方登录 验证密码/创建用户 都需要设置一个操作码 防止被恶意调用
     */
    public final static String THIRD_LOGIN_CODE = "third_login_code";

    /**
     * 角色相关
     */
    Integer ORDER_FOLLOWER = 1;
    Integer DOMESTIC_FINANCE = 2;
    Integer FOREIGN_FINANCE = 3;
    Integer CASHIER = 4;
    Integer MATERIAL_PURCHASE = 5;
    Integer ACCESSORY_PURCHASE = 6;
    Integer SHIPPING = 7;
    Integer WAREHOUSE = 8;
    Integer ORDER_FOLLOWER_MANAGER = 9;
    Integer DEPUTY_GENERAL_MANAGER = 10;

    /**
     * 审核状态
     */
    String AUDIT_PASS = "1";
    String AUDIT_ORIGIN = "0";

    /**
     * 费用类型
     */
    // ------- 国内 -------
    /* 报关 */
    String DOMESTIC_CUSTOMS_CLEARANCE_FEE = "gnbgf";
    /* 舱单 */
    String DOMESTIC_MANIFEST_FEE = "gncdf";
    /* 港杂费 */
    String DOMESTIC_PORT_FEE = "gngzf";
    /* 海运费 */
    String DOMESTIC_SHIPPING_FEE = "gnhyf";
    /* 陆运费 */
    String DOMESTIC_INLAND_FEE = "gnlyf";
    /* 送货费 */
    String DOMESTIC_DELIVER_FEE = "shf";

    // ------- 国外 ---------
    /* 国外清关费 */
    String FOREIGN_CUSTOMS_CLEARANCE_FEE = "gwqgf";
    /* 国外卡车费 */
    String FOREIGN_TRUCK_FEE = "gwkcf";
    String FOREIGN_TRUCK_FEE_1 = "gwkcf1";
    String FOREIGN_TRUCK_FEE_2 = "gwkcf2";
    String FOREIGN_TRUCK_FEE_3 = "gwkcf3";

    /* 国外查验费 */
    String FOREIGN_CHECK_FEE = "gwcyf";
    /* 清关关税 */
    String FOREIGN_CUSTOMS_CLEARANCE_TAX_FEE = "qggs";

    /* 额外费用 */
    String EXTRA_FEE_1 = "ewfy1";
    String EXTRA_FEE_2 = "ewfy2";
    String EXTRA_FEE_3 = "ewfy3";
    String EXTRA_FEE_4 = "ewfy4";
    String EXTRA_FEE_5 = "ewfy5";
    String EXTRA_FEE_6 = "ewfy6";


    /**
     * 供应商类型
     */
    String RAW_SUPPLIER = "1";
    String ACCESSORY_SUPPLIER = "2";

    /**
     * 原料采购方式
     */
    String PUR_EXW = "1";
    String PUR_FOB_PORT = "2";
    String PUR_FOB_FACTORY = "3";
    String PUR_CFR = "4";


    /**
     * 模块编码
     */
    String ORDER_MODULE = "1";
    String REMITTANCE_MODULE = "2";
    String RAW_MODULE = "3";
    String ACCESSORY_MODULE = "4";
    String FRETINFO_MODULE = "5";

    /**
     * 出入库方向
     */
    String ENTER_DIRECTION = "1";
    String EXIT_DIRECTION = "2";

    /**
     * 库存相关
     */
    // 辅料盘点
    String INVENTORY_FLPD = "flpd";
    String INVENTORY_FLCG = "flcg";
    String INVENTORY_FLCG_SINGLE = "flcg2";

    /**
     * 订单相关完成状态  finish_state
     */
    String ORDER_FINISH_INITIAL = "0";
    String ORDER_FINISH_HALF = "1";
    String ORDER_FINISH_COMPLETE = "2";



}
