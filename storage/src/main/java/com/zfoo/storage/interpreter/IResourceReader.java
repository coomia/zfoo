package com.zfoo.storage.interpreter;

import java.io.InputStream;
import java.util.List;

/**
 * 解释器模式设计的文本解析器
 * <p>
 * interpreter  [in'ter·pret·er || ɪn'tɜrprɪtə(r) /-'tɜːp-]
 * n.  直译程序, 翻译员, 解释者
 *
 * @author jaysunxiao
 * @version 1.0
 * @since 2017 07.12 14:49
 */
public interface IResourceReader {

    <T> List<T> read(InputStream inputStream, Class<T> clazz);

}
