package com.qihoo.wzws.rzb.action;

import com.daima.common.exception.NSException;
import com.qihoo.wzws.rzb.exception.SystemConfigException;
import com.qihoo.wzws.rzb.secure.AnalyzeSingle;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("xingtu")
public class RestAction {

    @Value("${output.report.path}")
    private String outputReportPath;

    /**
     * 返回路径
     *
     * @param logPath
     * @return
     * @throws SystemConfigException
     */
    @PostMapping("analyze")
    public Object analyze(String logPath) throws NSException {
        // 生成存储的目录
        String outPath = outputReportPath + File.separator + StringUtils.replace(UUID.randomUUID().toString(), "-", "") + File.separator;
        try {
            FileUtils.forceMkdir(new File(outPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        AnalyzeSingle.run(logPath, outPath);
        return outPath;
    }
}
