package ma.you.hospital.appointments.batch;

import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import ma.you.hospital.appointments.domain.Appointment;
import ma.you.hospital.appointments.domain.ArchivedAppointment;
import ma.you.hospital.appointments.repositories.AppointmentRepository;
import ma.you.hospital.appointments.repositories.ArchivedAppointmentRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class AppointmentsBatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    private final EntityManagerFactory entityManagerFactory;

    private final ArchivedAppointmentRepository archivedAppointmentRepository;
    private final AppointmentRepository appointmentRepository; // utile si tu veux plus tard supprimer les RDV

    // 1) JOB
    @Bean
    public Job archiveOldAppointmentsJob(Step archiveOldAppointmentsStep) {
        return new JobBuilder("archiveOldAppointmentsJob", jobRepository)
                .start(archiveOldAppointmentsStep)
                .build();
    }

    // 2) STEP (chunk)
    @Bean
    public Step archiveOldAppointmentsStep(JpaPagingItemReader<Appointment> appointmentItemReader,
                                           ItemProcessor<Appointment, ArchivedAppointment> appointmentItemProcessor,
                                           ItemWriter<ArchivedAppointment> archivedAppointmentItemWriter) {
        return new StepBuilder("archiveOldAppointmentsStep", jobRepository)
                .<Appointment, ArchivedAppointment>chunk(10, transactionManager)
                .reader(appointmentItemReader)
                .processor(appointmentItemProcessor)
                .writer(archivedAppointmentItemWriter)
                .build();
    }

    // 3) READER : RDV plus vieux que 6 mois
    @Bean
    public JpaPagingItemReader<Appointment> appointmentItemReader() {

        LocalDate limitDate = LocalDate.now().minusMonths(6);

        Map<String, Object> params = new HashMap<>();
        params.put("limitDate", limitDate);

        return new JpaPagingItemReaderBuilder<Appointment>()
                .name("appointmentItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(10)
                .queryString("SELECT a FROM Appointment a WHERE a.date < :limitDate")
                .parameterValues(params)
                .build();
    }

    // 4) PROCESSOR : Appointment -> ArchivedAppointment
    @Bean
    public ItemProcessor<Appointment, ArchivedAppointment> appointmentItemProcessor() {
        return appointment -> {
            ArchivedAppointment a = new ArchivedAppointment();
            // NE PAS toucher à a.setId(...)
            a.setAppointmentId(appointment.getId());
            a.setPatientId(appointment.getPatientId());
            a.setDoctorId(appointment.getDoctorId());
            a.setAppointmentDate(appointment.getDate());
            a.setAppointmentTime(appointment.getTime());
            a.setStatus(appointment.getStatus());
            return a;
        };
    }

    // 5) WRITER : sauvegarde les archives
    @Bean
    public ItemWriter<ArchivedAppointment> archivedAppointmentItemWriter() {
        return items -> archivedAppointmentRepository.saveAll(items);
    }
}
