package ma.you.hospital.appointments.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class ArchiveJobController {

    private final JobLauncher jobLauncher;
    private final Job archiveOldAppointmentsJob;

    @PostMapping("/archiveAppointments")
    public String runArchiveJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("run.id", System.currentTimeMillis())
                .toJobParameters();

        JobExecution execution = jobLauncher.run(archiveOldAppointmentsJob, params);
        return "Status : " + execution.getStatus();
    }
}
