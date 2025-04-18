package lk.ijse.medease.model;

import lk.ijse.medease.db.DBConnection;
import lk.ijse.medease.dto.PrescriptionDTO;
import lk.ijse.medease.dto.PrescriptionMedicineDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PrescriptionModel {

    public String addPrescription(PrescriptionDTO prescriptionDTO, ArrayList<PrescriptionMedicineDTO> presMedArray, int checkInNo, String doctorId) throws Exception {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            String prescriptionAddSql = "INSERT INTO prescription (doctor_id, patient_id, patient_age, diagnosis, notes) VALUES (?,?,?,?,?)";

            PreparedStatement prescriptionAddingStatement = connection.prepareStatement(prescriptionAddSql);
            prescriptionAddingStatement.setString(1, prescriptionDTO.getDoctorId());
            prescriptionAddingStatement.setInt(2, prescriptionDTO.getPatientId());
            prescriptionAddingStatement.setInt(3, prescriptionDTO.getAge());
            prescriptionAddingStatement.setString(4, prescriptionDTO.getDiagnosis());
            prescriptionAddingStatement.setString(5, prescriptionDTO.getNotes());

            boolean isPrescriptionSaved = prescriptionAddingStatement.executeUpdate() > 0 ? true : false;

            if (isPrescriptionSaved) {
                boolean isPrescriptionMedicineSaved = true;

                String presMedAddSql = "INSERT INTO prescription_medicine VALUES (?,?,?,?,?,?)";

                for (PrescriptionMedicineDTO dto : presMedArray) {
                    PreparedStatement presMedAddingStatement = connection.prepareStatement(presMedAddSql);
                    presMedAddingStatement.setInt(1, dto.getPrescriptionId());
                    presMedAddingStatement.setInt(2, dto.getMedicineId());
                    presMedAddingStatement.setString(3, dto.getName());
                    presMedAddingStatement.setString(4, dto.getDosage());
                    presMedAddingStatement.setString(5, dto.getFrequency());
                    presMedAddingStatement.setString(6, dto.getDuration());

                    if (!(presMedAddingStatement.executeUpdate() > 0)) {
                        isPrescriptionMedicineSaved = false;
                    }
                }

                if (isPrescriptionMedicineSaved) {

                    String checkInStatusUpdatingSql = "UPDATE check_in SET status = 'OUT' WHERE check_in_no = ? AND doctor_id = ?";

                    PreparedStatement checkInStatusUpdatingStatement = connection.prepareStatement(checkInStatusUpdatingSql);
                    checkInStatusUpdatingStatement.setInt(1, checkInNo);
                    checkInStatusUpdatingStatement.setString(2, doctorId);

                    boolean isCheckInTableUpdated = checkInStatusUpdatingStatement.executeUpdate() > 0 ? true : false;

                    if (isCheckInTableUpdated) {
                        return "Prescription Updated Successfully and Patient Checked Out";
                    } else {
                        connection.rollback();
                        return "Failed to Update Check-in Status";
                    }

                } else {
                    connection.rollback();
                    return "Failed to add Prescription_Medicine";
                }

            }else {
                connection.rollback();
                return "Failed to Add Prescription";
            }

        } catch (Exception e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    public ArrayList<PrescriptionDTO> getPatientHistory(int patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM prescription WHERE patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, patientId);
        ResultSet rst = statement.executeQuery();

        ArrayList<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();

        while (rst.next()){
            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(rst.getInt("prescription_id"), rst.getString("doctor_id"), rst.getInt("patient_id"), rst.getInt("patient_age"), rst.getString("diagnosis"), rst.getString("notes"));
            prescriptionDTOs.add(prescriptionDTO);
        }
        return prescriptionDTOs;
    }

    public ArrayList<PrescriptionMedicineDTO> getPrescriptionHistory(int patientId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT prescription_medicine.prescription_id, prescription_medicine.name, prescription_medicine.dosage, prescription_medicine.frequency, prescription_medicine.duration FROM prescription_medicine INNER JOIN prescription ON prescription_medicine.prescription_id = prescription.prescription_id WHERE prescription.patient_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, patientId);

        ResultSet rst = statement.executeQuery();

        ArrayList<PrescriptionMedicineDTO> prescriptionMedicineDTOs = new ArrayList<>();
        while (rst.next()){
            PrescriptionMedicineDTO presMedDto = new PrescriptionMedicineDTO(rst.getInt("prescription_id"), rst.getString("name"), rst.getString("dosage"), rst.getString("frequency"), rst.getString("duration"));
            prescriptionMedicineDTOs.add(presMedDto);
        }
        return prescriptionMedicineDTOs;
    }

    public ArrayList<PrescriptionMedicineDTO> getPrescriptionById(int prescriptionId) throws ClassNotFoundException, SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT name, dosage, frequency, duration FROM prescription_medicine WHERE prescription_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, prescriptionId);

        ResultSet rst = statement.executeQuery();

        ArrayList<PrescriptionMedicineDTO> prescriptionMedicineDTOs = new ArrayList<>();
        while (rst.next()){
            PrescriptionMedicineDTO presMedDto = new PrescriptionMedicineDTO(rst.getString("name"), rst.getString("dosage"), rst.getString("frequency"), rst.getString("duration"));
            prescriptionMedicineDTOs.add(presMedDto);
        }
        return prescriptionMedicineDTOs;
    }

//    public String updatePrescription(PrescriptionDTO prescriptionDTO) throws ClassNotFoundException, SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "UPDATE prescription SET doctor_id = ?, patient_id = ?, patient_age = ?, diagnosis = ?, notes = ? WHERE prescription_id = ?";
//
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, prescriptionDTO.getDoctorId());
//        statement.setInt(2, prescriptionDTO.getPatientId());
//        statement.setInt(3, prescriptionDTO.getAge());
//        statement.setString(4, prescriptionDTO.getDiagnosis());
//        statement.setString(5, prescriptionDTO.getNotes());
//        statement.setInt(6, prescriptionDTO.getPrescriptionId());
//
//        return statement.executeUpdate() > 0 ? "Prescription Updated Successfully" : "Failed to Update Prescription";
//    }
//
//    public String deletePrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "DELETE FROM prescription WHERE prescription_id = ?";
//
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, prescriptionId);
//
//        return statement.executeUpdate() > 0 ? "Prescription Deleted Successfully" : "Failed to Delete the Prescription";
//    }
//
//    public PrescriptionDTO searchPrescription(int prescriptionId) throws ClassNotFoundException, SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM prescription WHERE prescription_id = ?";
//
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setInt(1, prescriptionId);
//
//        ResultSet rst = statement.executeQuery();
//
//        if (rst.next()) {
//            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(rst.getInt("prescription_id"), rst.getString("doctor_id"), rst.getInt("patient_id"), rst.getInt("patient_age"), rst.getString("diagnosis"), rst.getString("notes"));
//            return prescriptionDTO;
//        } else {
//            return null;
//        }
//    }
//
//    public ArrayList<PrescriptionDTO> getAllPrescription() throws ClassNotFoundException, SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//
//        String sql = "SELECT * FROM prescription";
//
//        PreparedStatement statement = connection.prepareStatement(sql);
//        ResultSet rst = statement.executeQuery();
//
//        ArrayList<PrescriptionDTO> prescriptionDTOs = new ArrayList<>();
//
//        while (rst.next()){
//            PrescriptionDTO prescriptionDTO = new PrescriptionDTO(rst.getInt("prescription_id"), rst.getString("doctor_id"), rst.getInt("patient_id"), rst.getInt("patient_age"), rst.getString("diagnosis"), rst.getString("notes"));
//            prescriptionDTOs.add(prescriptionDTO);
//        }
//        return prescriptionDTOs;
//    }
}
