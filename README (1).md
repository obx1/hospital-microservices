# Backend – Hospital Management System (Microservices)

Ce projet fournit le **backend** d’une application de gestion hospitalière basée sur une architecture **microservices** avec **Spring Boot / Spring Cloud**.

👉 **Travail du développeur frontend** : consommer les API exposées par la **Gateway** (`http://localhost:9999`) pour construire un tableau de bord pour :
- les **patients**
- les **médecins**
- les **rendez-vous**
- la **facturation**
- (optionnel) les **rendez-vous archivés**

Il n’y a **pas d’authentification** pour l’instant.

---

## 1. Architecture (vue frontend)

Tu n’as pas besoin de connaître tous les détails côté Spring, mais pour comprendre :

- **Config Server** : configuration centrale (port `8888`)
- **Discovery Server (Eureka)** : registre des microservices (port `8761`)
- **Patients Service** : gère les patients
- **Doctors Service** : gère les médecins
- **Appointments Service** : gère les rendez-vous + batch d’archivage
- **Billing Service** : gère les factures et appelle Patients/Doctors (via Feign + Resilience4j)
- **API Gateway** : **point d’entrée unique** pour le frontend (port `9999`)

✅ **TOUS les appels HTTP doivent passer par :**

```text
http://localhost:9999
```

---

## 2. Comment démarrer le backend (pour les tests frontend)

### 2.1. Prérequis

- Java 21
- Maven
- Docker + Docker Compose

### 2.2. Lancer MySQL avec Docker

Dans le dossier du projet (là où se trouve `docker-compose.yml`) :

```bash
docker-compose up -d
```

### 2.3. Lancer les microservices (ordre recommandé)

1. **config-server**
2. **discovery-server**
3. **patients-service**
4. **doctors-service**
5. **appointments-service**
6. **billing-service**
7. **api-gateway**

Une fois démarré :

- Eureka : `http://localhost:8761`
- Gateway : `http://localhost:9999`

---

## 3. Base URL pour le frontend

Par exemple dans Angular :

```ts
// environment.ts
export const environment = {
  production: false,
  apiBaseUrl: 'http://localhost:9999'
};
```

Tous les services frontend devraient construire les URLs à partir de `apiBaseUrl`.

---

## 4. Modèles principaux (JSON)

### 4.1. Patient

```json
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@test.com",
  "birthDate": "1990-01-01"
}
```

### 4.2. Doctor

```json
{
  "id": 1,
  "name": "Dr Smith",
  "speciality": "Cardiology",
  "email": "dr.smith@test.com"
}
```

### 4.3. Appointment

```json
{
  "id": 3,
  "patientId": 1,
  "doctorId": 1,
  "date": "2025-12-01",
  "time": "10:30",
  "status": "PLANNED"
}
```

> **Dates** : format `YYYY-MM-DD`  
> **Heure** : chaîne `"HH:mm"`

### 4.4. Bill (facture enrichie)

Réponse du backend (via Billing service) :

```json
{
  "bill": {
    "id": 2,
    "appointmentId": 1,
    "doctorId": 1,
    "patientId": 1,
    "amount": 750.0,
    "billDate": "2025-11-28",
    "description": "Consultation + examens"
  },
  "doctor": {
    "id": 1,
    "name": "Dr Smith",
    "speciality": "Cardiology",
    "email": "dr.smith@test.com"
  },
  "patient": {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@test.com",
    "birthDate": "1990-01-01"
  }
}
```

⚠️ Avec **Resilience4j**, si le service Patients/Doctors tombe, on peut avoir :

```json
{
  "bill": { ... },
  "doctor": null,
  "patient": null
}
```

Le frontend doit donc gérer le cas où `doctor` ou `patient` est `null`.

---

## 5. Endpoints disponibles via la Gateway

### 👉 Base pour tous les endpoints

```text
http://localhost:9999
```

---

### 5.1. Patients

> La Gateway route `/patients/**` vers le microservice de patients.

#### GET `/patients`

Liste de tous les patients.

```bash
GET http://localhost:9999/patients
```

Réponse :

```json
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@test.com",
    "birthDate": "1990-01-01"
  }
]
```

#### GET `/patients/{id}`

```bash
GET http://localhost:9999/patients/1
```

#### POST `/patients`

Créer un patient.

```bash
POST http://localhost:9999/patients
Content-Type: application/json
```

Body :

```json
{
  "name": "John Doe",
  "email": "john.doe@test.com",
  "birthDate": "1990-01-01"
}
```

---

### 5.2. Doctors

> La Gateway route `/doctors/**` vers le microservice de doctors.

#### GET `/doctors`

```bash
GET http://localhost:9999/doctors
```

#### GET `/doctors/{id}`

```bash
GET http://localhost:9999/doctors/1
```

#### POST `/doctors`

```bash
POST http://localhost:9999/doctors
Content-Type: application/json
```

Body :

```json
{
  "name": "Dr Smith",
  "speciality": "Cardiology",
  "email": "dr.smith@test.com"
}
```

---

### 5.3. Appointments

> La Gateway route `/appointments/**` vers le microservice de rendez-vous.

#### GET `/appointments`

```bash
GET http://localhost:9999/appointments
```

#### GET `/appointments/{id}`

```bash
GET http://localhost:9999/appointments/3
```

#### POST `/appointments`

Créer un rendez-vous.

```bash
POST http://localhost:9999/appointments
Content-Type: application/json
```

Body :

```json
{
  "patientId": 1,
  "doctorId": 1,
  "date": "2025-12-01",
  "time": "10:30",
  "status": "PLANNED"
}
```

---

### 5.4. Billing (facturation)

> La Gateway route `/billing/**` vers `/api/bills/**` dans le microservice de billing.

#### GET `/billing/{id}`

Retourne une facture **enrichie** (bill + doctor + patient).

```bash
GET http://localhost:9999/billing/2
```

Réponse :

```json
{
  "bill": { },
  "doctor": { },
  "patient": { }
}
```

En cas de problème sur un microservice externe (Patients/Doctors), `doctor` ou `patient` peut être `null`.

---

### 5.5. Batch d’archivage (rendez-vous anciens)

Le backend inclut un traitement d’archivage des rendez-vous anciens (Spring Batch).

#### Lancer le job d’archivage

```bash
POST http://localhost:9999/appointments/batch/archive-old-appointments
```

Effet :

- Cherche les rendez-vous dont la date est avant une certaine limite (ex. > 6 mois).
- Copie ces rendez-vous dans une table `archived_appointments`.

#### Consulter les rendez-vous archivés

Si le contrôleur d’archives est exposé :

```bash
GET http://localhost:9999/appointments/archives
```

Réponse type :

```json
[
  {
    "id": 5,
    "originalAppointmentId": 12,
    "patientId": 1,
    "doctorId": 1,
    "appointmentDate": "2023-01-10",
    "status": "DONE",
    "archivedAt": "2025-11-29T22:50:00"
  }
]
```

---

## 6. Gestion des erreurs

Les erreurs sont celles par défaut de Spring Boot, par exemple :

```json
{
  "timestamp": "2025-11-29T22:57:28.371+01:00",
  "status": 404,
  "error": "Not Found",
  "path": "/patients/999"
}
```

Le frontend peut :

- utiliser `status` pour afficher un message utilisateur,
- lire `error` / `message` / `path` pour debug.

---

## 7. Swagger (documentation interactive)

Chaque microservice expose Swagger (springdoc-openapi).  
Exemples (ports à adapter selon ta config locale) :

- Patients Service : `http://localhost:8080/swagger-ui/index.html`
- Doctors Service : `http://localhost:8082/swagger-ui/index.html`
- Appointments Service : `http://localhost:8084/swagger-ui/index.html`
- Billing Service : `http://localhost:8085/swagger-ui/index.html`

Cela permet de :

- voir tous les endpoints,
- tester les requêtes / réponses,
- vérifier les modèles JSON.

---

## 8. Notes pour le développement frontend

- Toujours utiliser `http://localhost:9999` comme base.
- Gérer le cas où `doctor` ou `patient` peut être `null` dans `/billing/{id}`.
- Prévoir des services séparés côté frontend :
  - `PatientsService`
  - `DoctorsService`
  - `AppointmentsService`
  - `BillingService`
  - (optionnel) `ArchivesService` pour `/appointments/archives`
- En cas de problème de **CORS**, on ajoutera une configuration côté backend (actuellement aucune restriction spécifique n’est configurée).

