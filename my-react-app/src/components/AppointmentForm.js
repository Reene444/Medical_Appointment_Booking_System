import React, { useState } from 'react';
import axios from "axios";


function AppointmentForm({ onAppointmentAdded }) {
    const [patient_id, setPatientId] = useState(0);
    const [doctor_id, setDoctorId] = useState(0);
    const [date, setDate] = useState('');
    const [time, setTime] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        const appointment = { patient_id, doctor_id, date, time };
        try {
            // 这里假设发送请求到后端的代码
            await axios.post('http://localhost:8095/api/v1/appointments', appointment);
            onAppointmentAdded();
        } catch (error) {
            console.error('Error creating appointment:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h2>Create New Appointment</h2>
            <div>
                <label>Patient ID</label>
                <input type="number" value={patient_id} onChange={(e) => setPatientId(Number(e.target.value))} />
            </div>
            <div>
                <label>Doctor ID</label>
                <input type="number" value={doctor_id} onChange={(e) => setDoctorId(Number(e.target.value))} />
            </div>
            <div>
                <label>Date</label>
                <input type="date" value={date} onChange={(e) => setDate(e.target.value)} />
            </div>
            <div>
                <label>Time</label>
                <input type="time" value={time} onChange={(e) => setTime(e.target.value)} />
            </div>
            <button type="submit">Create Appointment</button>
        </form>
    );
}

export default AppointmentForm;