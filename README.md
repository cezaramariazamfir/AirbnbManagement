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
![Diagrama](meniu_diagrama.jpg)


### 🔌 Persistenta datelor – JDBC

- Sistemul foloseste **JDBC (Java Database Connectivity)** pentru interactiune cu baza de date.
 <br/> 
- Am definit un **DAO generic de baza (`BasicDAO`)** cu operatii CRUD:
  - `create(T entity)`
  - `read(int id)`
  - `update(T entity)`
  - `delete(int id)`
  - `readAll()`
- Clasele DAO concrete implementeaza aceasta interfata: `ProprietarDAO`, `VilaDAO`, `CamperDAO`, `ApartamentDAO`.
- Suplimentar am implementat si operatii de citire pentru `RezervareDAO`.
- Clasele DAO sunt implementate folosind **pattern-ul Singleton** pentru a garanta ca exista o singura instanta a fiecarui DAO pe durata rularii aplicatiei.
- Pentru unele enitiati, am adaugat **metode suplimentare** in DAO-uri pentru a acoperi cerinte specifice (ex. gasirea tutoror proprietatilor pentru un proprietar anume).
<br/>
- Conexiunea cu baza de date este gestionata prin clasa `DatabaseConfiguration`, care implementeaza un singleton pentru conexiunea JDBC folosind `DriverManager`.  
<br/>
- `printStackTrace()` afiseaza detalii despre eroarea SQL, utile pentru depanare.
- `PreparedStatement` e folosit pentru a construi si executa intructiuni SQL cu parametri siguri.
- `executeUpdate()` executa intructiuni care modifica datele in DB si intoarce numarul de randuri modificate.




## 📊 Rapoarte și statistici  
*(Ex. venituri generate, grad de ocupare, rezervări anulate etc.)*

## ⚙️ Servicii și acțiuni disponibile  
*(Ex. adăugare/ștergere/modificare proprietate, rezervări, căutări)*
