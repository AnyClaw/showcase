package com.example.showcase.service;

import com.example.showcase.dto.response.GroupBriefDTO;
import com.example.showcase.dto.response.GroupStudentResult;
import com.example.showcase.dto.response.GroupWithStudentsDTO;
import com.example.showcase.dto.response.StudentBriefDTO;
import com.example.showcase.exception.UserNotFoundException;
import com.example.showcase.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupService {

    private final GroupRepository groupRepository;

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


}
