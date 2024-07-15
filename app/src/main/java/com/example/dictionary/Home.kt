    package com.example.dictionary

    import androidx.compose.foundation.border
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.OutlinedTextField
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.res.colorResource
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp

    @Composable
    fun Home(viewModel: DictionaryViewModel ) {
        var inputWord by remember { mutableStateOf("") }
//        viewModel.getMeaning(inputWord)
        val wordlist: List<WordResult>? = viewModel.wordResult
        val error: String? = viewModel.errorMessage
        val isLoading: Boolean = viewModel.isLoading
//        val state = rememberScrollableState(consumeScrollDelta = true)
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceAround

            ) {
                OutlinedTextField(
                    value = inputWord.trim(),
                    onValueChange = { inputWord = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    shape = RoundedCornerShape(8.dp),
                    label = { Text("Enter Word") }
                )
                Button(
                    onClick = { viewModel.getMeaning(inputWord.trim()) },
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.MY_PRIMARY)),
                    modifier = Modifier.padding(10.dp)

                ) {
                    Text("Search",)
                }
            }
            if(isLoading){
                Text("Loading...")
            }
            if (error != null) {
                Text(error, color = Color.Red, modifier = Modifier.padding(vertical = 8.dp))
            }
            wordlist?.let { wordList ->
                if (wordList.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        // Display the word and phonetic once
                        item {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp)
                            ) {
                                Text(
                                    text = "${wordList[0].word ?: ""}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 30.sp,
                                    color = Color.Blue
                                )
                 /*line120*/             Text(text = "${wordList[0].phonetic ?: ""}", color = Color.Gray)
                            }
                        }

                        // Loop through each word result
                        items(wordList) { wordResult ->
                            // Display meanings
                            wordResult.meanings.forEach { meaning ->
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .border(
                                            width = 2.dp,
                                            color = Color.Gray,
                                            shape = RoundedCornerShape(8.dp)
                                        )
                                        .padding(8.dp)

                                ) {
                                    Text(
                                        text = meaning.partOfSpeech,
                                        color = colorResource(id = R.color.MY_PRIMARY),
                                        fontSize = 20.sp
                                    )
                                    Text(text = "Definitions:", fontWeight = FontWeight.Bold)
                                    meaning.definitions.forEachIndexed { index,definition ->
                                        Text(text = "${index+1}. ${definition.definition}")
                                    }

                                }
                                Spacer(modifier = Modifier.height(12.dp))
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }
            }
        }

    }