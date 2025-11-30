package ma.you.hospital.appointments.batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArchiveJobScheduler {

    private final JobLauncher jobLauncher;
    private final Job archiveOldAppointmentsJob;

    /**
     * Tous les jours à 2h du matin.
     * Pour tester plus vite, tu peux changer le cron.
     */
    @Scheduled(cron = "0 0 2 * * ?")
    public void launchArchiveJob() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis()) // job unique à chaque run
                .toJobParameters();

        jobLauncher.run(archiveOldAppointmentsJob, params);
    }
}
