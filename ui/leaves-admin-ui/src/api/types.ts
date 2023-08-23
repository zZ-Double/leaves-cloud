/**
 * 分页数据对象
 */
export interface PageResult<T> {
    /**
     * 数据列表
     */
    records: T;
    /**
     * 数据总数
     */
    total: number;
}

/**
 * 分页查询参数
 */
export interface PageQuery {
    /**
     * 分页页码
     */
    current?: number;
    /**
     * 每页数量
     */
    size?: number;
}