package com.example.showcase.service;

import com.example.showcase.dto.request.AssignStudentsToGroupRequest;
import com.example.showcase.dto.request.CreateGroupRequest;
import com.example.showcase.dto.response.*;
import com.example.showcase.entity.Group;
import com.example.showcase.entity.User;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.repository.GroupRepository;
import com.example.showcase.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    public List<GroupBriefDTO> getMyGroups(Integer teacherId) {
        if (teacherId == null) {
            throw new UserNotFoundException("ID преподавателя не может быть null");
        }

        return groupRepository.findGroupsByTeacherId(teacherId);
    }

    public List<GroupWithStudentsDTO> getGroupsWithStudents(Integer teacherId, String groupName) {
        if (teacherId == null) {
            throw new UserNotFoundException("ID преподавателя не может быть null");
        }
        String cleanGroupName = (groupName != null && !groupName.isBlank()) ? groupName : null;

        List<GroupStudentResult> rows = groupRepository.findGroupsWithStudentsByTeacherId(teacherId, cleanGroupName);
        Map<Integer, List<GroupStudentResult>> groupedByGroup = rows.stream()
                .collect(Collectors.groupingBy(
                        GroupStudentResult::groupId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));
        return groupedByGroup.entrySet().stream()
                .map(entry -> {
                    List<GroupStudentResult> groupRows = entry.getValue();
                    String currentGroupName = groupRows.get(0).groupName();

                    List<StudentBriefDTO> students = groupRows.stream()
                            .filter(row -> row.studentId() != null)
                            .map(row -> new StudentBriefDTO(
                                    row.studentId(),
                                    row.firstName(),
                                    row.lastName(),
                                    row.middleName(),
                                    row.email()
                            ))
                            .toList();
                    return new GroupWithStudentsDTO(entry.getKey(), currentGroupName, students);
                })
                .toList();
    }

    @Transactional
    public GroupCreatedDTO createGroup(CreateGroupRequest request) {
        userRepository.findById(request.teacherId())
                .orElseThrow(() -> new UserNotFoundException("Преподаватель с ID " + request.teacherId() + " не найден"));

        Group newGroup = new Group();
        newGroup.setGroupName(request.groupName());
        newGroup.setUserId(request.teacherId());

        Group savedGroup = groupRepository.save(newGroup);

        return new GroupCreatedDTO(savedGroup.getGroupId(), savedGroup.getGroupName(), savedGroup.getUserId());
    }

    @Transactional
    public void assignStudentsToGroup(Integer groupId, AssignStudentsToGroupRequest request) {
        if (groupId != null) {
            if (!groupRepository.existsById(Long.valueOf(groupId))) {
                throw new IllegalArgumentException("Группа с ID " + groupId + " не найдена");
            }
        }

        if (request.userIds() == null || request.userIds().isEmpty()) {
            log.warn("Список ID студентов пуст. Нечего назначать в группу.");
            return;
        }

        groupRepository.assignStudentsToGroup(groupId, request.userIds());

        log.info("Студенты {} назначены в группу {}", request.userIds(), groupId);
    }

    @Transactional
    public void removeStudentsFromGroup(Integer groupId, AssignStudentsToGroupRequest request) {
        if (groupId == null) {
            throw new IllegalArgumentException("ID группы не может быть null");
        }

        if (!groupRepository.existsById(Long.valueOf(groupId))) {
            throw new IllegalArgumentException("Группа с ID " + groupId + " не найдена");
        }

        if (request.userIds() == null || request.userIds().isEmpty()) {
            log.warn("Список ID студентов пуст. Нечего удалять из группы.");
            return;
        }

        groupRepository.removeStudentsFromGroupNative(groupId, request.userIds());

        log.info("Студенты {} удалены из группы {}", request.userIds(), groupId);
    }

    public List<GroupWithStudentsDTO> getAllGroupsWithStudents(String groupName) {
        String cleanGroupName = (groupName != null && !groupName.isBlank()) ? groupName : null;

        List<GroupStudentResult> rows = groupRepository.findAllGroupsWithStudents(cleanGroupName);

        Map<Integer, List<GroupStudentResult>> groupedByGroup = rows.stream()
                .collect(Collectors.groupingBy(
                        GroupStudentResult::groupId,
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        return groupedByGroup.entrySet().stream()
                .map(entry -> {
                    List<GroupStudentResult> groupRows = entry.getValue();
                    String currentGroupName = groupRows.get(0).groupName();

                    List<StudentBriefDTO> students = groupRows.stream()
                            .filter(row -> row.studentId() != null)
                            .map(row -> new StudentBriefDTO(
                                    row.studentId(),
                                    row.firstName(),
                                    row.lastName(),
                                    row.middleName(),
                                    row.email()
                            ))
                            .toList();

                    return new GroupWithStudentsDTO(entry.getKey(), currentGroupName, students);
                })
                .toList();
    }



}
