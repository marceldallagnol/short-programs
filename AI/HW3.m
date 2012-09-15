f = fopen('vocab.txt');
words = textscan(f,'%s','\n');              %Scan the vocabulary from file
words = char(words{1});
nWords = length(words);                     %Number of words
fclose(f);
f = fopen('unigram.txt');                   %Number of occurences per word
unigram = textscan(f,'%f','\n');
fclose(f);
f = fopen('bigram.txt');                    %Pairs of words and number of occurences
bigram = textscan(f,'%f %f %f','\n');
fclose(f);

f = fopen('output.txt','w');                %Output file
unigramFreqs = unigram{1}/sum(unigram{1});  %Convert to frequencies

%%%%%%%%%%LETTER A%%%%%%%%%%%
fprintf(f,'a) Words that begin with the letter T and respective frequencies:\r\n\r\n');
for i = 1:nWords                            %Find and print frequency of words that begin with 'T'
    if(words(i,1) == 'T')
        if(strcmp(strtrim(words(i,:)),'THE'))%Find index of 'THE'
            the = i;
        end
        fprintf(f,'%s %f\r\n', words(i,:), unigramFreqs(i));
    end
end

currentWord = 1;                            %Initialize bigrams to first word's
bigramEstimate = zeros(nWords);             %500 by 500 array for combinations of every word with every other
for i = 1:length(bigram{1})                  %Set bigram occurences
    if(bigram{1}(i) ~= currentWord)
        currentWord = bigram{1}(i);
    end
    bigramEstimate(currentWord,bigram{2}(i)) = bigram{3}(i);
end

followThe = bigramEstimate(the,:);         %Ocurrences of words that follow 'THE'
followThe = followThe/unigram{1}(the);      %Turn occurences to frequencies
%%%%%%%%%%%LETTER B%%%%%%%%%%%%%%
fprintf(f,'\r\nb) 10 words that most follow the word THE and respective frequencies:\r\n\r\n');
for i = 1:10                               %Find words that most follow 'THE'
    [mx index] = max(followThe);
    fprintf(f,'%s %f\r\n',words(index,:),mx);
    followThe(index) = 0;
end

sentence = {'<s>' 'THE' 'STOCK' 'MARKET' 'FELL' 'BY' 'ONE' 'HUNDRED' 'POINTS' 'LAST' 'WEEK'};
for i = 1:11                               %Find indexes of words in sentence
    j = 1;
    while(strcmp(strtrim(words(j,:)),sentence{i}) == 0)
    j = j + 1;
    end
    sentence{i} = j;
end

sentenceFreqs = zeros(2,11);               %Frequencies of words in sentence, unigram and bigram
Lu = 1;
for i = 1:10                               %Calculate likelihood (unigram)
    sentenceFreqs(1,i) = unigramFreqs(sentence{i+1});
    Lu = Lu * sentenceFreqs(1,i);
end
Lu = log(Lu);                            %Convert to log-likelihood

Lb = 1;
for i = 1:10                               %Calculate likelihood (bigram)
    sentenceFreqs(2,i) = bigramEstimate(sentence{i},sentence{i+1})/unigram{1}(sentence{i});
    Lb = Lb * sentenceFreqs(2,i);
end
Lb = log(Lb);                            %Convert to log-likelihood

%%%%%%%%%%%%LETTER C%%%%%%%%%
fprintf(f,'\r\nc) Log-likelihood of the sentence "The stock market fell by one hundred points last week", under unigram and bigram models:\r\n\r\n');
fprintf(f,'Lu = %f\r\nLb = %f\r\n',Lu,Lb);

sentence = {'<s>' 'THE' 'SIXTEEN' 'OFFICIALS' 'SOLD' 'FIRE' 'INSURANCE'};
for i = 1:7                                %Find indexes of words in sentence
    j = 1;
    while(strcmp(strtrim(words(j,:)),sentence{i}) == 0)
    j = j + 1;
    end
    sentence{i} = j;
end

Lu = 1;
for i = 1:6                                %Calculate likelihood (unigram)
    sentenceFreqs(1,i) = unigramFreqs(sentence{i+1});
    Lu = Lu * sentenceFreqs(1,i);
end
Lu = log(Lu);                            %Convert to log-likelihood

Lb = 1;
for i = 1:6                                %Calculate likelihood (bigram)
    sentenceFreqs(2,i) = bigramEstimate(sentence{i},sentence{i+1})/unigram{1}(sentence{i});
    Lb = Lb * sentenceFreqs(2,i);
end
Lb = log(Lb);                            %Convert to log-likelihood

%%%%%%%%LETTER D%%%%%%%%
fprintf(f,'\r\nd) Log-likelihood of the sentence "The sixteen officials sold fire insurance", under unigram and bigram models:\r\n\r\n');
fprintf(f,'Lu = %f\r\nLb = %f\r\n',Lu,Lb);
fprintf(f,'\r\nPairs of words not present in the training corpus:');
for i = 1:6                               %Find pairs of words not matched in database
    if(sentenceFreqs(2,i) == 0)
        fprintf(f,' %s|%s   ',strtrim(words(sentence{i+1},:)),strtrim(words(sentence{i},:)));
    end
end

lambda = 0:0.01:1;
Lm = ones(1,101);
for i = 1:6                              %Calculate mixture model likelihood
    sentenceFreqs(1,i) = unigramFreqs(sentence{i+1});
    sentenceFreqs(2,i) = bigramEstimate(sentence{i},sentence{i+1})/unigram{1}(sentence{i});
    Lm = Lm .* ((1-lambda) * sentenceFreqs(1,i) + lambda * sentenceFreqs(2,i));
end
mixedModel = log(Lm);                  %Convert to log-likelihood

%%%%%%%%%%%LETTER E%%%%%%%%%%%%%%%
plot(lambda,mixedModel);
xlabel('Lambda');
ylabel('Mixed model log-likelihood');
[mx index] = max(mixedModel);
fprintf(f,'\r\n\r\ne) Optimal value for lambda in mixture model: %.2f\r\n   Corresponding mixed log-likelihood: %.2f\r\nPlot is automatically generated.',lambda(index),mx);
fclose(f);