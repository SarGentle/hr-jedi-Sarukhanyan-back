package ru.lanit.bpm.jedu.hrjedi.app.impl.vacation;

import lombok.RequiredArgsConstructor;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.task.Task;
import org.springframework.stereotype.Component;
import ru.lanit.bpm.jedu.hrjedi.app.api.vacation.FindVacationsToApproveInbound;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FindVacationsToApproveUseCase implements FindVacationsToApproveInbound {

    private final TaskService taskService;

    @Override
    public Set<String> execute(String approverLogin) {

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(approverLogin).active().list();

        return tasks.stream().map(Task::getProcessInstanceId).collect(Collectors.toSet());
    }
}
