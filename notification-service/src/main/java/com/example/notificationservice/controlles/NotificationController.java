package com.example.notificationservice.controlles;

import com.example.notificationservice.exception.Error;
import com.example.notificationservice.services.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
@CrossOrigin("*")
public class NotificationController {

    private final INotificationService iNotificationService;

//    @GetMapping
//    public ResponseEntity<List<NotificationDto>> all(@RequestHeader("id") Long id)
//    {
//        return new ResponseEntity<>(iNotificationService.allNotificationByMember(id), HttpStatus.OK);
//    }

    @PatchMapping("/{key}")
    public ResponseEntity<Error> markNotifAsSeen(@PathVariable String key)
    {
        iNotificationService.markAsSeenFirebase(key);
        return new ResponseEntity<>(new Error("Notification mark as seen."), HttpStatus.OK);
    }

    @DeleteMapping("/{key}")
    public ResponseEntity<Error> delete(@PathVariable String key)
    {
        iNotificationService.deleteNotificationFirebase(key);
        return new ResponseEntity<>(new Error("Notification deleted successfully."), HttpStatus.OK);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<NotificationDto> byId(@PathVariable Long id, @RequestHeader("id") Long userId)
//    {
//        return new ResponseEntity<>(iNotificationService.byId(id, userId), HttpStatus.OK);
//    }
}
