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
- id_proprietar: int 
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

### 🛎️ Rezervare
- id_rezervare: int  
- propritate: Proprietate  
- dataStart: LocalDate  
- dataEnd: LocalDate  
- costTotal: double  
- status: Status (enum)

  ### 👁️ Vizualizare  
- nume_proprietate: String    
- id_proprietar: int  
- dataVizualizare: LocalDateTime    


### 🧾 Enum Status

```java
public enum Status {
    ANULATA,
    CONFIRMATA,
    ACHITATA
}
```

## 🧭 Meniu  
![Diagrama](meniu_diagrama.png)

## 📊 Rapoarte și statistici  
*(Ex. venituri generate, grad de ocupare, rezervări anulate etc.)*

## ⚙️ Servicii și acțiuni disponibile  
*(Ex. adăugare/ștergere/modificare proprietate, rezervări, căutări)*

## 💾 Persistență și audit  
*(Salvare date cu JDBC, fișier CSV pentru audit acțiuni)*
