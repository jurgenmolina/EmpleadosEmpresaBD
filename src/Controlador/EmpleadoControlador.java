/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import DATA.EmpleadoDAO;
import Modelo.Empleado;
import Vista.IngresarEmpleado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jurgenmolina <jurgenmolina29@gmail.com>
 */
public class EmpleadoControlador implements ActionListener {

    private Empleado empleado = new Empleado();
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private IngresarEmpleado vista;

    public EmpleadoControlador() {
    }

    public EmpleadoControlador(Empleado empleado, EmpleadoDAO empleadoDAO, IngresarEmpleado vista) {
        this.empleado = empleado;
        this.empleadoDAO = empleadoDAO;
        this.vista = vista;
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnModificar.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
    }


    public void iniciar() {
        vista.setTitle("Copa America");
        vista.setLocationRelativeTo(null);
        vista.setVisible(true);
    }
    
    

    public void comboID() {
        vista.comboID.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        
        if (arg0.getSource() == vista.btnGuardar) {

            empleado.setId(0);
            empleado.setNombre(vista.txtNombre.getText());
            empleado.setDia(vista.txtDia.getText());
            empleado.setHora(vista.txtHora.getText());

            if (empleado.getNombre().isEmpty() || empleado.getDia().isEmpty() || empleado.getHora().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Complete los campos");
            } else {
                boolean confirmacion = duplicado(empleado.getNombre());

                if (confirmacion) {
                    JOptionPane.showMessageDialog(null, "Ya se encuentra registrado, verifique el nombre");
                } else {
                    empleadoDAO.create(empleado);
                    JOptionPane.showMessageDialog(null, "Ingresado con exito");
                    limpiar();
                }
            }

        }

        if (arg0.getSource() == vista.btnModificar) {

            int id = Integer.parseInt(vista.comboID.getSelectedItem().toString());
            empleado.setId(id);
            empleado.setNombre(vista.txtNombre.getText());
            empleado.setDia(vista.txtDia.getText());
            empleado.setHora(vista.txtHora.getText());

            if (empleado.getNombre().isEmpty() || empleado.getDia().isEmpty() || empleado.getHora().isEmpty() ) {
                JOptionPane.showMessageDialog(null, "Complete los campos");
            } else {
                empleadoDAO.update(empleado);
                JOptionPane.showMessageDialog(null, "Actualizado con exito");
                limpiar();
            }
        }

        if (arg0.getSource() == vista.btnEliminar) {
            int id = Integer.parseInt(vista.comboID.getSelectedItem().toString());

            empleado.setId(id);

            empleadoDAO.delete(empleado);
            JOptionPane.showMessageDialog(null, "Eliminado con exito");
            limpiar();
        }

        if (arg0.getSource() == vista.comboID) {

            List<Empleado> list = new ArrayList<>();
            list = empleadoDAO.getList();

            String opcion = (String) vista.comboID.getSelectedItem();
            int o = Integer.parseInt(opcion);

            if (!list.isEmpty()) {
                for (Empleado p : list) {
                    if (p.getId() == o) {
                        vista.txtNombre.setText(p.getNombre());
                        vista.txtDia.setText(String.valueOf(p.getDia()));
                        vista.txtHora.setText(String.valueOf(p.getHora()));
                    }
                }
            }
        }


    }

    public void limpiar() {

        vista.txtHora.setText("");
        vista.txtNombre.setText("");
        vista.txtDia.setText("");

    }

    public void llenarModeloComboBox() {

        List<Empleado> list = new ArrayList<>();
        list = empleadoDAO.getList();

        if (!list.isEmpty()) {

            for (int i = 0; i < list.size(); i++) {
                this.vista.comboID.addItem(String.valueOf(list.get(i).getId()));
            }
            return;
        }
        this.vista.comboID.addItem("vacio");
    }

    private boolean duplicado(String nombre) {

        List<Empleado> list = new ArrayList<>();
        list = empleadoDAO.getList();
        boolean confirmacion = false;

        for (Empleado c : list) {
            if (c.getNombre().equals(nombre)) {
                confirmacion = true;
            }
        }
        return confirmacion;

    }

}
