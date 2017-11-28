package Biblioteca;

import java.util.regex.*;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class frm extends javax.swing.JFrame {

    public frm() {
        initComponents();
        this.setTitle("Patrones");
        this.setLocationRelativeTo(null);
        this.setResizable(false);
    }
    

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtTexto = new javax.swing.JTextField();
        btnComprobar = new javax.swing.JButton();
        btnComprobar1 = new javax.swing.JButton();
        btnComprobar2 = new javax.swing.JButton();
        btnComprobar3 = new javax.swing.JButton();
        btnComprobar4 = new javax.swing.JButton();
        btnComprobar5 = new javax.swing.JButton();
        btnComprobar6 = new javax.swing.JButton();
        btnComprobar7 = new javax.swing.JButton();
        btnComprobar8 = new javax.swing.JButton();
        btnCorreo = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Texto:");

        btnComprobar.setText(" contiene exactamente el patron \"abc\"");
        btnComprobar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobarActionPerformed(evt);
            }
        });

        btnComprobar1.setText("contiene \"abc\"");
        btnComprobar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar1ActionPerformed(evt);
            }
        });

        btnComprobar2.setText("empieza por \"abc\"");
        btnComprobar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar2ActionPerformed(evt);
            }
        });

        btnComprobar3.setText("empieza por \"abc\" ó \"Abc\"");
        btnComprobar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar3ActionPerformed(evt);
            }
        });

        btnComprobar4.setText("letras min/max");
        btnComprobar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar4ActionPerformed(evt);
            }
        });

        btnComprobar5.setText("no empieza con digito");
        btnComprobar5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar5ActionPerformed(evt);
            }
        });

        btnComprobar6.setText("no acaba con digito");
        btnComprobar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar6ActionPerformed(evt);
            }
        });

        btnComprobar7.setText("solo contienen los caracteres a ó b");
        btnComprobar7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar7ActionPerformed(evt);
            }
        });

        btnComprobar8.setText("contiene un 1 y ese 1 no esta seguido por un 2");
        btnComprobar8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprobar8ActionPerformed(evt);
            }
        });

        btnCorreo.setText("verificar correo");
        btnCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorreoActionPerformed(evt);
            }
        });

        jButton1.setText("verificar estudiante");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnComprobar1)
                                .addGap(143, 143, 143)
                                .addComponent(jButton1))
                            .addComponent(btnComprobar2)
                            .addComponent(btnComprobar3)
                            .addComponent(btnComprobar4)
                            .addComponent(btnComprobar5)
                            .addComponent(btnComprobar6)
                            .addComponent(btnComprobar7)
                            .addComponent(btnComprobar8))
                        .addContainerGap(21, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnComprobar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCorreo)
                        .addGap(41, 41, 41))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtTexto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnComprobar)
                    .addComponent(btnCorreo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnComprobar1)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnComprobar2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnComprobar3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobar4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobar5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobar6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobar7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnComprobar8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnComprobarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobarActionPerformed
        // Comprobar si el String cadena contiene exactamente el patr�n (matches) 
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("abc");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobarActionPerformed

    private void btnComprobar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar1ActionPerformed
        // Comprobar si el String cadena contiene 
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile(".*abc.*");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar1ActionPerformed

    private void btnComprobar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar2ActionPerformed
        // Comprobar si el String cadena empieza por 
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("^abc.*");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar2ActionPerformed

    private void btnComprobar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar3ActionPerformed
        // Comprobar si el String cadena empieza por "abc" ó "Abc"
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("^[aA]bc.*");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar3ActionPerformed

    private void btnComprobar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar4ActionPerformed
        // Comprobar si el String cadena está formado por un mínimo de 5 letras mayúsculas o minúsculas y un máximo de 10
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("[a-zA-Z]{5,10}");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar4ActionPerformed

    private void btnComprobar5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar5ActionPerformed
        //  Comprobar si el String cadena no empieza por un dígito
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("^[^\\d].*");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar5ActionPerformed

    private void btnComprobar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar6ActionPerformed
        //  Comprobar si el String cadena no acaba con un dígito
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile(".*[^\\d]$");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar6ActionPerformed

    private void btnComprobar7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar7ActionPerformed
        //  Comprobar si el String cadena solo contienen los caracteres a ó b
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("(a|b)+");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar7ActionPerformed

    private void btnComprobar8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprobar8ActionPerformed
        //  Comprobar si el String cadena contiene un 1 y ese 1 no está seguido por un 2
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile(".*1(?!2).*");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnComprobar8ActionPerformed

    private void btnCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorreoActionPerformed
        //  Validar correos
        String dominios[]={"hotmail","gmail","yahoo","live","outlook"};
        String terminados[]={"com","es"};
        String correo=txtTexto.getText();
        boolean valido=false;
        for (String dom : dominios) {
            for (String term : terminados) {
                Pattern pat = Pattern.compile("^[a-z]+[a-z0-9_-]{3,15}@{1,1}" + dom + "{1,1}.{1,1}" + term + "{1,1}");
                Matcher mat = pat.matcher(correo);
                if(mat.matches()){
                    valido=true;
                    break;
                }
            }
            if(valido){
                break;
            }
        }
        if (valido) {
            JOptionPane.showMessageDialog(rootPane,"Correo Valido");
        } else {
            JOptionPane.showMessageDialog(rootPane,"Correo invalido","ERROR",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnCorreoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //verifica si tiene al principio e o E y 10 dígitos
        String cadena=txtTexto.getText();
        Pattern pat = Pattern.compile("[e|E][0-9]{10,10}");
        Matcher mat = pat.matcher(cadena);
        if (mat.matches()) {
            JOptionPane.showMessageDialog(rootPane,"SI","MENSAJE",JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(rootPane,"NO","MENSAJE",JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnComprobar;
    private javax.swing.JButton btnComprobar1;
    private javax.swing.JButton btnComprobar2;
    private javax.swing.JButton btnComprobar3;
    private javax.swing.JButton btnComprobar4;
    private javax.swing.JButton btnComprobar5;
    private javax.swing.JButton btnComprobar6;
    private javax.swing.JButton btnComprobar7;
    private javax.swing.JButton btnComprobar8;
    private javax.swing.JButton btnCorreo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField txtTexto;
    // End of variables declaration//GEN-END:variables
    
    
    
}