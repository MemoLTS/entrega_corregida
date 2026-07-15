CREATE TABLE tienda (
    id_tienda BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    codigo_tienda VARCHAR(255),
    direccion VARCHAR(255),
    comuna VARCHAR(255),
    ciudad VARCHAR(255),
    region VARCHAR(255),
    telefono VARCHAR(255),
    estado VARCHAR(255),
    fecha_creacion DATETIME
);

CREATE TABLE asignacion_personal (
    id_asignacion BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_tienda BIGINT NOT NULL,
    id_empleado BIGINT NOT NULL,
    nombre_empleado VARCHAR(255) NOT NULL,
    cargo VARCHAR(255) NOT NULL,
    fecha_inicio DATETIME,
    fecha_termino DATETIME,
    estado_asignacion VARCHAR(255),
    CONSTRAINT fk_asignacion_tienda FOREIGN KEY (id_tienda) REFERENCES tienda (id_tienda)
);

CREATE TABLE horario_personal (
    id_horario_personal BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_asignacion BIGINT NOT NULL,
    dia_semana VARCHAR(255) NOT NULL,
    hora_inicio TIME,
    hora_termino TIME,
    turno VARCHAR(255),
    activo BOOLEAN,
    CONSTRAINT fk_horario_personal_asignacion FOREIGN KEY (id_asignacion) REFERENCES asignacion_personal (id_asignacion)
);

CREATE TABLE horario_tienda (
    id_horario_tienda BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_tienda BIGINT NOT NULL,
    dia_semana VARCHAR(255) NOT NULL,
    hora_apertura TIME,
    hora_cierre TIME,
    activo BOOLEAN,
    CONSTRAINT fk_horario_tienda_tienda FOREIGN KEY (id_tienda) REFERENCES tienda (id_tienda)
);

CREATE TABLE producto_asociado_tienda (
    id_producto_asociado BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_tienda BIGINT NOT NULL,
    id_producto BIGINT NOT NULL,
    nombre_producto VARCHAR(255),
    visible_en_tienda BOOLEAN NOT NULL,
    CONSTRAINT fk_producto_asociado_tienda_tienda FOREIGN KEY (id_tienda) REFERENCES tienda (id_tienda)
);

CREATE TABLE reporte_tienda (
    id_reporte BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_tienda BIGINT NOT NULL,
    tipo_reporte VARCHAR(255) NOT NULL,
    periodo_inicio DATETIME,
    periodo_fin DATETIME,
    fecha_generacion DATETIME,
    CONSTRAINT fk_reporte_tienda_tienda FOREIGN KEY (id_tienda) REFERENCES tienda (id_tienda)
);
