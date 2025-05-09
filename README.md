# 🏡 AirbnbManagement

Tema aleasa: Un sistem de management al proprietatilor date spre inchiriere pe termen scurt, asemanator cu Airbnb.

---

## 📦 Clase

### 👤 Proprietar
- id_proprietar: int  
- nume: String  
- email: String  
- parola: String  

### 🏠 Proprietate (clasa abstracta)
- id_proprietate: int  
- denumire: String  
- locatie: String  
- capacitate: int  
- disponibila: boolean  
- pretPeNoapte: double  

#### 🏢 Apartament (mosteneste Proprietate)
- etaj: int  
- areBalcon: boolean  

#### 🏡 Vila (mosteneste Proprietate)
- suprafataGradina: double  
- arePiscina: boolean  

#### 🚐 Camper (mosteneste Proprietate)
- lungime: double  
- autonomieApa: int (in zile)

### 🗓️ CalendarProprietate
- id_proprietate: int  
- disponibilitati: Map<LocalDate,Boolean>

### 🛎️ Rezervare
- id_rezervare: int  
- propritate: Proprietate  
- dataStart: LocalDate  
- dataEnd: LocalDate  
- costTotal: double  
- status: Status (enum)

### 🧾 Enum Status

```java
public enum Status {
    IN_ASTEPTARE,
    CONFIRMATA,
    ACHITATA
}
