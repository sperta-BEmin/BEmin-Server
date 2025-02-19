package run.bemin.api.comment.controller;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import run.bemin.api.comment.dto.CommentDto;
import run.bemin.api.comment.dto.CommentRequestDto;
import run.bemin.api.comment.dto.CommentResponseDto;
import run.bemin.api.comment.service.ChatGPTService;
import run.bemin.api.comment.service.CommentService;
import run.bemin.api.general.response.ApiResponse;
import run.bemin.api.product.dto.MessageResponseDto;

@RequestMapping("/api/comment")
@RestController
@RequiredArgsConstructor
public class CommentController {
  private final ChatGPTService chatGPTService;
  private final CommentService commentService;

  @GetMapping("")
  public ApiResponse<CommentResponseDto> getNewComment(@RequestBody CommentRequestDto commentRequestDto) {
    String comment = chatGPTService.getChatGPTResponse(commentRequestDto.userPrompt());
    return ApiResponse.from(HttpStatus.OK, "성공", new CommentResponseDto(comment));
  }

  @GetMapping("/{product_id}")
  public ApiResponse<MessageResponseDto> saveComment(@PathVariable String productId,
                                                     @RequestBody CommentDto commentDto)
  {
    commentService.saveComment(UUID.fromString(productId),commentDto.content());
    return ApiResponse.from(HttpStatus.OK, "성공", new MessageResponseDto("Comment가 저장되었습니다."));
  }

}
