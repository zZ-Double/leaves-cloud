/**
 * Option
 */
export interface Option {
    children?: Option[];
    /**
     * 选项的标签
     */
    label: string;
    /**
     * 选项的值
     */
    value: string;
}