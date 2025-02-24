package com.sprint.mission.discodeit.controller;

import com.sprint.mission.discodeit.entity.BinaryContent;
import com.sprint.mission.discodeit.service.BinaryContentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.UUID;

@Controller
@ResponseBody
@RequestMapping("/api/binaryContent")
@RequiredArgsConstructor
public class BinaryContentController {
    private final BinaryContentService binaryContentService;

    //파일 단건 조회
    @RequestMapping(path = "find")
    public ResponseEntity<BinaryContent> find(@RequestParam("binaryContentId") UUID binaryContentId){
        BinaryContent binaryContent = binaryContentService.findById(binaryContentId);
        return ResponseEntity.status(HttpStatus.OK).body(binaryContent);
    }
    //파일 다건 조회
    @RequestMapping(path = "findAllByIdIn")
    public ResponseEntity<List<BinaryContent>> findAllByIdIn(@RequestParam("binaryContentIds") List<UUID> binaryContentIds){
        List<BinaryContent> binaryContents = binaryContentService.findAllByIdIn(binaryContentIds);
        return ResponseEntity.status(HttpStatus.OK).body(binaryContents);
    }
}
