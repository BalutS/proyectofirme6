package com.controlador;

import com.modelo.*; // For model classes like Estudiante, Profesor, Curso, Asignatura
import com.dao.AsignaturaDAO;
import com.dao.CursoDAO;
import com.dao.EstudianteDAO;
import com.dao.ProfesorDAO;
import com.dao.impl.AsignaturaDaoImpl;
import com.dao.impl.CursoDaoImpl;
import com.dao.impl.EstudianteDaoImpl;
import com.dao.impl.ProfesorDaoImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ControladorAdmin { // Removed "extends ControladorGeneral"

    private final EstudianteDAO estudianteDAO;
    private final ProfesorDAO profesorDAO;
    private final CursoDAO cursoDAO;
    private final AsignaturaDAO asignaturaDAO;
    // private final Colegio colegio; // Removed this field

    // Primary constructor for Dependency Injection
    public ControladorAdmin(EstudianteDAO estudianteDAO, ProfesorDAO profesorDAO, CursoDAO cursoDAO, AsignaturaDAO asignaturaDAO) {
        // super(colegio); // Removed
        // this.colegio = colegio; // Removed
        this.estudianteDAO = estudianteDAO;
        this.profesorDAO = profesorDAO;
        this.cursoDAO = cursoDAO;
        this.asignaturaDAO = asignaturaDAO;
    }
    
    // Secondary constructor for direct instantiation
    public ControladorAdmin(){
        // super(Colegio.getInstance()); // Removed, pass Colegio.getInstance() if superclass needs it, but it's being removed.
        // this.colegio = Colegio.getInstance(); // Removed
        // Instantiate default DAOs. In a real app, these would typically be injected.
        this.estudianteDAO = new EstudianteDaoImpl();
        this.profesorDAO = new ProfesorDaoImpl();
        this.cursoDAO = new CursoDaoImpl();
        this.asignaturaDAO = new AsignaturaDaoImpl();
    }

    public void agregarEstudiante(Estudiante estudiante) {
        if (estudiante == null) return;
        estudianteDAO.save(estudiante); 

        if (estudiante.getCurso() != null) {
            Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(
                estudiante.getCurso().getGrado(), estudiante.getCurso().getGrupo()
            );
            if (cursoOpt.isPresent()) {
                Curso cursoDelEstudiante = cursoOpt.get();
                if (cursoDelEstudiante.getEstudiantes() == null) {
                    cursoDelEstudiante.setEstudiantes(new ArrayList<>());
                }
                if (cursoDelEstudiante.getEstudiantes().stream().noneMatch(e -> e.getCodigo() == estudiante.getCodigo())) {
                    cursoDelEstudiante.getEstudiantes().add(estudiante);
                }
                cursoDAO.update(cursoDelEstudiante); 
            }
        }
    }

    public void agregarProfesor(Profesor profesor) {
        if (profesor == null) return;
        profesorDAO.save(profesor); 

        if (profesor.getCurso() != null) {
            Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(
                profesor.getCurso().getGrado(), profesor.getCurso().getGrupo()
            );
            if (cursoOpt.isPresent()) {
                Curso cursoDelProfesor = cursoOpt.get();
                cursoDelProfesor.setProfesor(profesor); 
                cursoDAO.update(cursoDelProfesor); 
            }
        }
    }

    public Estudiante buscarEstudiante(int codigo) {
        return estudianteDAO.getById(codigo).orElse(null);
    }

    public Profesor buscarProfesor(int codigo) {
        return profesorDAO.getById(codigo).orElse(null);
    }

    public void agregarCurso(Curso curso) {
        if (curso == null) return;
        cursoDAO.save(curso);
    }

    public void asignarCursoAProfesor(int codProf, int grado, int grupo) {
        Optional<Profesor> profOpt = profesorDAO.getById(codProf);
        Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(grado, grupo);

        if (profOpt.isPresent() && cursoOpt.isPresent()) {
            Profesor prof = profOpt.get();
            Curso curso = cursoOpt.get();
            
            prof.setCurso(curso); 
            
            profesorDAO.update(prof); 
            cursoDAO.update(curso);   
        }
    }
    
    public boolean existeCurso(int grado, int grupo) {
        return cursoDAO.getByGradoAndGrupo(grado, grupo).isPresent();
    }
    
    public void asignarEstudianteACurso(int codEst, int grado, int grupo) {
        Optional<Estudiante> estOpt = estudianteDAO.getById(codEst);
        Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(grado, grupo);

        if (estOpt.isPresent() && cursoOpt.isPresent()) {
            Estudiante est = estOpt.get();
            Curso curso = cursoOpt.get();
            
            est.setCurso(curso); 
            
            if (curso.getEstudiantes() == null) {
                curso.setEstudiantes(new ArrayList<>());
            }
            if (curso.getEstudiantes().stream().noneMatch(e -> e.getCodigo() == est.getCodigo())) {
                curso.getEstudiantes().add(est);
            }
            
            estudianteDAO.update(est); 
            cursoDAO.update(curso);   
        }
    }

    public void crearAsignatura(String nombreAsignatura, Curso cursoSeleccionado) {
        if (nombreAsignatura == null || nombreAsignatura.trim().isEmpty()) return;

        Asignatura nuevaAsignatura = new Asignatura(nombreAsignatura);
        asignaturaDAO.save(nuevaAsignatura); 

        if (cursoSeleccionado != null) {
            Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(cursoSeleccionado.getGrado(), cursoSeleccionado.getGrupo());
            if (cursoOpt.isPresent()) {
                Curso cursoActualizado = cursoOpt.get();
                if (cursoActualizado.getAsignaturas() == null) {
                    cursoActualizado.setAsignaturas(new ArrayList<>());
                }
                if (cursoActualizado.getAsignaturas().stream().noneMatch(a -> a.getNombre().equalsIgnoreCase(nombreAsignatura))) {
                    cursoActualizado.getAsignaturas().add(nuevaAsignatura);
                }
                cursoDAO.update(cursoActualizado); 

                if (cursoActualizado.getEstudiantes() != null) {
                    for (Estudiante est : new ArrayList<>(cursoActualizado.getEstudiantes())) { 
                        if (est.getAsignaturas() == null) {
                            est.setAsignaturas(new ArrayList<>());
                        }
                        if (est.getAsignaturas().stream().noneMatch(a -> a.getNombre().equalsIgnoreCase(nombreAsignatura))) {
                            est.getAsignaturas().add(nuevaAsignatura);
                        }
                        estudianteDAO.update(est);
                    }
                }
            }
        }
    }


    public void asignarAsignaturaAEstudiante(int codEst, String nombreAsig) {
        Optional<Estudiante> estOpt = estudianteDAO.getById(codEst);
        Optional<Asignatura> asigOpt = asignaturaDAO.getByName(nombreAsig);

        if (estOpt.isPresent() && asigOpt.isPresent()) {
            Estudiante est = estOpt.get();
            Asignatura asig = asigOpt.get();
            if (est.getAsignaturas() == null) {
                est.setAsignaturas(new ArrayList<>());
            }
            if (est.getAsignaturas().stream().noneMatch(a -> a.getNombre().equalsIgnoreCase(asig.getNombre()))) {
                 est.getAsignaturas().add(asig);
            }
            estudianteDAO.update(est);
        }
    }

    public String generarReporteEstudiante(int codigo) {
        Optional<Estudiante> estOpt = estudianteDAO.getById(codigo);
        return estOpt.map(Estudiante::reporteAcademico).orElse("Estudiante no encontrado.");
    }

    public String listarTodosLosCursos() {
        List<Curso> cursos = cursoDAO.getAll();
        if (cursos.isEmpty()) return "No hay cursos registrados.";
        StringBuilder sb = new StringBuilder("--- LISTA DE CURSOS ---\n");
        for (Curso curso : cursos) {
            sb.append(curso.obtenerDescripcionCompleta()).append("\n\n");
        }
        return sb.toString();
    }
    
    public String listarTodosLosProfesores() {
        List<Profesor> profesores = profesorDAO.getAll();
        if (profesores.isEmpty()) return "No hay docentes registrados.";
        StringBuilder sb = new StringBuilder("--- LISTA DE DOCENTES ---\n");
        for (Profesor prof : profesores) {
            sb.append(prof.obtenerDescripcionCompleta()).append("\n\n");
        }
        return sb.toString();
    }
    
    public String listarTodosLosEstudiantes() {
        List<Estudiante> estudiantes = estudianteDAO.getAll();
        if (estudiantes.isEmpty()) return "No hay estudiantes registrados.";
        StringBuilder sb = new StringBuilder("--- LISTA DE ESTUDIANTES ---\n");
        for (Estudiante est : estudiantes) {
            sb.append(est.obtenerDescripcionCompleta()).append("\n\n");
        }
        return sb.toString();
    }
    
    public List<Curso> getCursos() {
        return cursoDAO.getAll();
    }

    public List<Asignatura> getAsignaturas() { 
        return asignaturaDAO.getAll();
    }

    public boolean existeCodigoEstudiante(int codigo) {
        return estudianteDAO.getById(codigo).isPresent();
    }

    public boolean existeAsignaturaGlobalmente(String nombreAsignatura) {
        return asignaturaDAO.getByName(nombreAsignatura).isPresent();
    }

    public boolean eliminarEstudiante(int codigo) {
        Optional<Estudiante> estOpt = estudianteDAO.getById(codigo);
        if (estOpt.isPresent()) {
            Estudiante estudiante = estOpt.get();
            if (estudiante.getCurso() != null) {
                Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(estudiante.getCurso().getGrado(), estudiante.getCurso().getGrupo());
                if (cursoOpt.isPresent()) {
                    Curso cursoDelEstudiante = cursoOpt.get();
                    if (cursoDelEstudiante.getEstudiantes() != null) {
                        cursoDelEstudiante.getEstudiantes().removeIf(e -> e.getCodigo() == codigo);
                        cursoDAO.update(cursoDelEstudiante);
                    }
                }
            }
            estudianteDAO.delete(codigo);
            return true;
        }
        return false;
    }

    public boolean eliminarProfesor(int codigo) {
        Optional<Profesor> profOpt = profesorDAO.getById(codigo);
        if (profOpt.isPresent()) {
            Profesor profesor = profOpt.get();
            if (profesor.getCurso() != null) {
                Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(profesor.getCurso().getGrado(), profesor.getCurso().getGrupo());
                if (cursoOpt.isPresent()) {
                    Curso cursoDelProfesor = cursoOpt.get();
                    if (cursoDelProfesor.getProfesor() != null && cursoDelProfesor.getProfesor().getCodigo() == codigo) {
                        cursoDelProfesor.setProfesor(null);
                        cursoDAO.update(cursoDelProfesor);
                    }
                }
            }
            profesorDAO.delete(codigo);
            return true;
        }
        return false;
    }

    public boolean eliminarCurso(int grado, int grupo) {
        Optional<Curso> cursoOpt = cursoDAO.getByGradoAndGrupo(grado, grupo);
        if (cursoOpt.isPresent()) {
            Curso cursoAEliminar = cursoOpt.get();
            if (cursoAEliminar.getEstudiantes() != null) {
                for (Estudiante est : new ArrayList<>(cursoAEliminar.getEstudiantes())) { 
                    est.setCurso(null);
                    estudianteDAO.update(est);
                }
            }
            if (cursoAEliminar.getProfesor() != null) {
                Profesor prof = cursoAEliminar.getProfesor();
                prof.setCurso(null);
                profesorDAO.update(prof);
            }
            cursoDAO.delete(grado, grupo);
            return true;
        }
        return false;
    }

    public boolean eliminarAsignatura(String nombreAsignatura) {
        Optional<Asignatura> asigOpt = asignaturaDAO.getByName(nombreAsignatura);
        if (asigOpt.isPresent()) {
            List<Curso> todosLosCursos = cursoDAO.getAll();
            for (Curso curso : todosLosCursos) {
                if (curso.getAsignaturas() != null) {
                    boolean modified = curso.getAsignaturas().removeIf(a -> a.getNombre().equalsIgnoreCase(nombreAsignatura));
                    if (modified) {
                        cursoDAO.update(curso);
                    }
                }
            }
            List<Estudiante> todosLosEstudiantes = estudianteDAO.getAll();
            for (Estudiante est : todosLosEstudiantes) {
                 if (est.getAsignaturas() != null) {
                    boolean modified = est.getAsignaturas().removeIf(a -> a.getNombre().equalsIgnoreCase(nombreAsignatura));
                    if (modified) {
                        estudianteDAO.update(est);
                    }
                 }
            }
            asignaturaDAO.delete(nombreAsignatura);
            return true;
        }
        return false;
    }
    
    // The autenticar method for non-admin users, if needed by Principal, should be here now.
    // It was previously in ControladorGeneral.
    public boolean autenticarPersona(int codigo, String tipo) {
        // Note: DAOs use Colegio.getInstance(), so this 'colegio' field isn't strictly necessary
        // if Colegio.getInstance() is consistently used by DAOs.
        // However, if other logic in this class (not yet refactored) were to use 'this.colegio',
        // then having it from the constructor is fine.
        // For authentication, it's cleaner to use DAOs if possible.
        if (tipo.equalsIgnoreCase("Profesor")) {
            return profesorDAO.getById(codigo).isPresent();
        } else if (tipo.equalsIgnoreCase("Estudiante")) {
            return estudianteDAO.getById(codigo).isPresent();
        }
        return false;
    }
}
