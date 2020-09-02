package com.yzl.judgehost.controller.v1;

import com.yzl.judgehost.core.authorization.AuthorizationRequired;
import com.yzl.judgehost.dto.JudgeDTO;
import com.yzl.judgehost.dto.SingleJudgeResultDTO;
import com.yzl.judgehost.exception.http.ForbiddenException;
import com.yzl.judgehost.service.JudgeService;
import com.yzl.judgehost.utils.JudgeHolder;
import com.yzl.judgehost.vo.JudgeConditionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.RejectedExecutionException;

/**
 * 判题接口（Controller）
 *
 * @author yuzhanglong
 * @date 2020-7-6 21:57
 */
@RestController
@RequestMapping("/judge")

public class JudgeController {
    private final JudgeService judgeService;

    @Autowired
    public JudgeController(JudgeService judgeService) {
        this.judgeService = judgeService;
    }

    /**
     * 执行判题
     *
     * @param judgeDTO 判题相关数据传输对象
     * @author yuzhanglong
     * @date 2020-7-1 21:00
     */
    @PostMapping("/run")
    @AuthorizationRequired
    public Object runJudge(@RequestBody @Validated JudgeDTO judgeDTO) {
        CompletableFuture<List<SingleJudgeResultDTO>> judgeResults;
        try {
            judgeResults = judgeService.runJudge(judgeDTO);
        } catch (RejectedExecutionException e) {
            throw new ForbiddenException("B1005");
        }
        return "success!";
    }
}