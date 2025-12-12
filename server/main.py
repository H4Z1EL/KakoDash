from fastapi import FastAPI
from pydantic import BaseModel
import json
import os

app = FastAPI()

PROFILE_FILE = "profile.json"


# ---------------------------
# MODELO DE DATOS QUE USA TU APP
# ---------------------------
class Profile(BaseModel):
    name: str
    color: str   # Tu app usa strings: "cyan", "green", "magenta", "yellow"


# ---------------------------
# FUNCIONES PARA GUARDAR Y CARGAR
# ---------------------------
def load_profile():
    # Si no existe, crear uno con valores por defecto
    if not os.path.exists(PROFILE_FILE):
        default_profile = {"name": "Jugador", "color": "cyan"}
        save_profile(default_profile)
        return default_profile
    
    # Cargar desde archivo
    with open(PROFILE_FILE, "r") as f:
        return json.load(f)


def save_profile(data):
    with open(PROFILE_FILE, "w") as f:
        json.dump(data, f)


# ---------------------------
# ENDPOINTS DEL SERVIDOR
# ---------------------------

# GET /profile
@app.get("/profile")
def get_profile():
    return load_profile()


# PUT /profile
@app.put("/profile")
def update_profile(profile: Profile):
    data = {"name": profile.name, "color": profile.color}
    save_profile(data)
    return data


# DELETE /profile
@app.delete("/profile")
def delete_profile():
    default_profile = {"name": "Jugador", "color": "cyan"}
    save_profile(default_profile)
    return {"message": "Perfil reiniciado"}
