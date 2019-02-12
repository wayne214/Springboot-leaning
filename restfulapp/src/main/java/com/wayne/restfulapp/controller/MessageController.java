package com.wayne.restfulapp.controller;

import com.wayne.restfulapp.model.Message;
import com.wayne.restfulapp.repository.MessageRepository;
import io.swagger.annotations.*;
import javafx.scene.chart.ValueAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "消息",description = "消息操作 API", position = 100, protocols = "http")
@RestController
@RequestMapping("/")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    /**
     * 获取所有消息体
     * @return
     */
    @ApiOperation(
            value = "消息列表",
            notes = "完整的消息列表",
            produces="application/json, application/xml",
            consumes="application/json, application/xml",
            response = List.class
    )
    @GetMapping(value = "messages")
    public List<Message> list() {
        List<Message> messages = this.messageRepository.findAll();
        return messages;
    }

    /**
     * 创建消息体
     * @param message
     * @return
     */
    @ApiOperation(
            value = "添加消息",
            notes = "根据参数创建消息"
    )
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "消息 ID", required = true, dataType = "Long", paramType = "query"),
            @ApiImplicitParam(name = "text", value = "正文", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "summary", value = "摘要", required = false, dataType = "String", paramType = "query"),
    })
    @PostMapping(value = "message")
    public Message create(Message message) {
        message = this.messageRepository.save(message);
        return message;
    }

    /**
     * 使用put更新消息体
     * @param message
     * @return
     */
    @ApiOperation(
            value = "修改消息",
            notes = "根据参数修改消息"
    )
    @ApiResponses({
            @ApiResponse(code = 100, message = "请求参数有误"),
            @ApiResponse(code = 101, message = "未授权"),
            @ApiResponse(code = 103, message = "禁止访问"),
            @ApiResponse(code = 104, message = "请求路径不存在"),
            @ApiResponse(code = 200, message = "服务器内部错误")
    })
    @PutMapping(value = "message")
    public Message modify(Message message) {
        Message msgResult = this.messageRepository.update(message);
        return msgResult;
    }

    /**
     * 更新text字段
     * @param message
     * @return
     */
    @PatchMapping(value = "/message/text")
    public Message patch(Message message) {
        return this.messageRepository.updateText(message);
    }

    @GetMapping(value = "message/{id}")
    public Message get(@PathVariable Long id) {
       return this.messageRepository.findMessage(id);
    }

    @DeleteMapping(value = "message/{id}")
    public void delete(@PathVariable("id") Long id) {
        this.messageRepository.deleteMessage(id);
    }

}
