package com.example.kakodash.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.AlertDialog
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kakodash.viewmodel.ItemViewModel

@Composable
fun ItemsScreen(vm: ItemViewModel = viewModel()) {

    val items by vm.items.collectAsState()
    val editingItem by vm.editingItem.collectAsState()
    val loading by vm.loading.collectAsState()

    var showDialog by remember { mutableStateOf(false) }
    var title by remember { mutableStateOf("") }
    var body by remember { mutableStateOf("") }


    LaunchedEffect(Unit) {
        vm.loadItems()
    }

    LaunchedEffect(editingItem) {
        editingItem?.let {
            title = it.title
            body = it.body
            showDialog = true
        }
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Button(
            onClick = {
                vm.startEdit(null)
                title = ""
                body = ""
                showDialog = true
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Crear nuevo item")
        }

        Spacer(Modifier.height(16.dp))

        LazyColumn {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text(item.title, style = MaterialTheme.typography.titleLarge)
                        Text(item.body, style = MaterialTheme.typography.bodyMedium)

                        Row(Modifier.fillMaxWidth(), horizontalArrangement =
                            Arrangement.SpaceBetween) {
                            TextButton(onClick = {
                                vm.startEdit(item)
                            }) {
                                Text("Editar")
                            }
                            TextButton(onClick = {
                                vm.deleteItem(item.id)
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                Button(onClick = {
                    vm.saveItem(title, body)
                    showDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text(if (editingItem == null) "Nuevo Item" else "Editar Item") },
            text = {
                Column {
                    OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título")
                    })
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(value = body, onValueChange = { body = it }, label = {
                        Text("Contenido") })
                }
            }
        )
    }
    if (loading) {
        LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
    }
}